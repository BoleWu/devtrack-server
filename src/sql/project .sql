CREATE TABLE project (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '项目ID',
                         name VARCHAR(100) NOT NULL COMMENT '项目名称',
                         description TEXT COMMENT '项目描述',
                         status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '项目状态：ACTIVE进行中 / DONE已完成 / ARCHIVED已归档',
                         create_by BIGINT NOT NULL COMMENT '创建人用户ID',
                         create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标志：0未删除，1已删除'
) COMMENT='项目表';
