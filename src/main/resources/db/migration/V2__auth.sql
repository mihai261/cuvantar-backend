DROP TABLE IF EXISTS auth_session;
CREATE TABLE auth_session (
    id int NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    username varchar(255) NOT NULL,
    token varchar(255) NOT NULL,
    PRIMARY KEY (id),
    INDEX (user_id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);