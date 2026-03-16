CREATE TABLE operation_log (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                               user_id BIGINT NOT NULL COMMENT '操作人',
                               biz_type VARCHAR(30) NOT NULL COMMENT '业务类型: PROJECT / TASK',
                               biz_id BIGINT NOT NULL COMMENT '业务ID',
                               action VARCHAR(50) NOT NULL COMMENT '动作: CREATE / UPDATE / DELETE / ASSIGN / STATUS',
                               detail TEXT COMMENT '变更详情(JSON)',
                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
) COMMENT='操作日志表';
