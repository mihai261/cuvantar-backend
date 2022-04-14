DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id int NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS flashcard;
CREATE TABLE flashcard (
    id int NOT NULL AUTO_INCREMENT,
    front varchar(255) NOT NULL,
    back varchar(255) NOT NULL,
    definition text NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS review;
CREATE TABLE review (
    id int NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    card_id int NOT NULL,
    level int NOT NULL,
    due_date timestamp NOT NULL,
    PRIMARY KEY (id),
    INDEX (user_id),
    FOREIGN KEY(user_id) REFERENCES user(id),
    INDEX (card_id),
    FOREIGN KEY(card_id) REFERENCES flashcard(id)
);