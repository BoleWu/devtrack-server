CREATE TABLE user (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(50),
                      password VARCHAR(255),
                      role VARCHAR(20),
                      status TINYINT
);
