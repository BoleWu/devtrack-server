-- 登录日志表
CREATE TABLE login_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    ip VARCHAR(45) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    success BOOLEAN DEFAULT FALSE COMMENT '是否登录成功',
    failure_reason VARCHAR(255) COMMENT '失败原因',
    create_time DATETIME NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';