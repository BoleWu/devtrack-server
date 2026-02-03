CREATE TABLE task (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
                      project_id BIGINT NOT NULL COMMENT '所属项目ID',
                      title VARCHAR(200) NOT NULL COMMENT '任务标题',
                      description TEXT COMMENT '任务描述',
                      status VARCHAR(20) DEFAULT 'TODO' COMMENT '任务状态：TODO待办 / DOING进行中 / DONE已完成',
                      create_by BIGINT NOT NULL COMMENT '创建人用户ID',
                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标志：0未删除，1已删除'
) COMMENT='任务表';
