package com.devtrack.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库初始化器
 * 在应用启动时检查并创建数据库和表
 */
@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${database.init.sql-path:classpath*:db/sql/*.sql}")
    private String sqlPath;

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("开始执行数据库初始化检查...");
        
        try {
            // 1. 检查并创建数据库
            checkAndCreateDatabase();
            
            // 2. 检查并创建缺失的表
            checkAndCreateTables();
            
            log.info("数据库初始化检查完成！");
        } catch (Exception e) {
            log.error("数据库初始化失败", e);
            throw e;
        }
    }

    /**
     * 检查并创建数据库
     */
    private void checkAndCreateDatabase() throws Exception {
        // 从URL中提取数据库名称
        String dbName = extractDatabaseName(datasourceUrl);
        String baseUrl = extractBaseUrl(datasourceUrl);
        
        log.info("检查数据库是否存在: {}", dbName);
        
        // 连接到MySQL服务器（不指定数据库）
        String serverUrl = baseUrl + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        
        try (Connection conn = DriverManager.getConnection(serverUrl, username, password);
             Statement stmt = conn.createStatement()) {
            
            // 检查数据库是否存在
            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
            
            if (!rs.next()) {
                log.warn("数据库 {} 不存在，正在创建...", dbName);
                stmt.execute("CREATE DATABASE `" + dbName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci");
                log.info("数据库 {} 创建成功！", dbName);
            } else {
                log.info("数据库 {} 已存在", dbName);
            }
        }
    }

    /**
     * 检查并创建缺失的表
     */
    private void checkAndCreateTables() throws Exception {
        log.info("开始检查表结构...");
        
        // 读取SQL文件
        List<String> tableSqls = readTableCreationSqls();
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            
            for (String sql : tableSqls) {
                // 提取表名
                String tableName = extractTableName(sql);
                if (tableName == null) {
                    continue;
                }
                
                // 检查表是否存在
                if (!tableExists(conn, tableName)) {
                    log.warn("表 {} 不存在，正在创建...", tableName);
                    
                    // 执行建表语句
                    try {
                        stmt.execute(sql);
                        log.info("表 {} 创建成功！", tableName);
                    } catch (Exception e) {
                        log.error("创建表 {} 失败: {}", tableName, e.getMessage());
                    }
                } else {
                    log.debug("表 {} 已存在", tableName);
                }
            }
        }
        
        log.info("表结构检查完成！");
    }

    /**
     * 检查表是否存在
     */
    private boolean tableExists(Connection conn, String tableName) throws Exception {
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
    }

    /**
     * 读取SQL文件并解析建表语句
     */
    private List<String> readTableCreationSqls() throws Exception {
        List<String> sqlList = new ArrayList<>();
        
        // 尝试从多个位置读取SQL文件
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
        // 1. 首先尝试从classpath读取
        Resource[] resources = resolver.getResources(sqlPath);
        
        // 2. 如果classpath没有找到，尝试从项目根目录读取
        if (resources.length == 0) {
            try {
                String projectRootSqlPath = "file:db/sql/*.sql";
                resources = resolver.getResources(projectRootSqlPath);
                log.info("从项目根目录读取SQL文件: {}", projectRootSqlPath);
            } catch (Exception e) {
                log.warn("从项目根目录读取SQL文件失败: {}", e.getMessage());
            }
        }
        
        if (resources.length == 0) {
            log.warn("未找到SQL文件，跳过表创建检查");
            return sqlList;
        }
        
        for (Resource resource : resources) {
            log.info("读取SQL文件: {}", resource.getFilename());
            
            // 读取文件内容
            String content = new String(resource.getInputStream().readAllBytes());
            
            // 解析建表语句
            List<String> createTableStatements = parseCreateTableStatements(content);
            sqlList.addAll(createTableStatements);
        }
        
        log.info("共解析到 {} 个建表语句", sqlList.size());
        return sqlList;
    }

    /**
     * 解析SQL文件中的建表语句
     */
    private List<String> parseCreateTableStatements(String sqlContent) {
        List<String> statements = new ArrayList<>();
        
        // 按DROP TABLE IF EXISTS分割
        String[] parts = sqlContent.split("(?i)DROP TABLE IF EXISTS");
        
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            
            // 找到CREATE TABLE语句
            int createIndex = part.toLowerCase().indexOf("create table");
            if (createIndex == -1) {
                continue;
            }
            
            // 提取完整的CREATE TABLE语句
            String remaining = part.substring(createIndex);
            
            // 找到语句结束位置（下一个分号或文件末尾）
            int endIndex = remaining.indexOf(";");
            if (endIndex == -1) {
                endIndex = remaining.length();
            }
            
            String createStatement = remaining.substring(0, endIndex).trim();
            
            // 添加分号
            if (!createStatement.endsWith(";")) {
                createStatement += ";";
            }
            
            statements.add(createStatement);
        }
        
        return statements;
    }

    /**
     * 从建表语句中提取表名
     */
    private String extractTableName(String createTableSql) {
        // 匹配 CREATE TABLE `table_name` 或 CREATE TABLE table_name
        Pattern pattern = Pattern.compile(
            "(?i)CREATE\\s+TABLE\\s+(?:IF\\s+NOT\\s+EXISTS\\s+)?[`\"']?(\\w+)[`\"']?"
        );
        
        Matcher matcher = pattern.matcher(createTableSql);
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }

    /**
     * 从JDBC URL中提取数据库名称
     */
    private String extractDatabaseName(String url) {
        // 移除参数部分
        String urlWithoutParams = url.split("\\?")[0];
        
        // 提取最后一个/后面的内容
        int lastSlashIndex = urlWithoutParams.lastIndexOf("/");
        if (lastSlashIndex != -1 && lastSlashIndex < urlWithoutParams.length() - 1) {
            return urlWithoutParams.substring(lastSlashIndex + 1);
        }
        
        throw new IllegalArgumentException("无法从URL中提取数据库名称: " + url);
    }

    /**
     * 从JDBC URL中提取基础URL（不包含数据库名）
     */
    private String extractBaseUrl(String url) {
        // 移除参数部分
        String urlWithoutParams = url.split("\\?")[0];
        
        // 找到数据库名称前的部分
        int lastSlashIndex = urlWithoutParams.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return urlWithoutParams.substring(0, lastSlashIndex);
        }
        
        throw new IllegalArgumentException("无法从URL中提取基础URL: " + url);
    }
}
