CREATE TABLE project_member (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                                project_id BIGINT NOT NULL COMMENT '项目ID',
                                user_id BIGINT NOT NULL COMMENT '成员用户ID',
                                role VARCHAR(20) DEFAULT 'MEMBER' COMMENT '成员角色: OWNER / MEMBER',
                                create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
                                deleted TINYINT DEFAULT 0 COMMENT '是否删除',
                                UNIQUE KEY uk_project_user (project_id, user_id)
) COMMENT='项目成员表';
