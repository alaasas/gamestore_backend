DROP DATABASE IF EXISTS GameDB;
CREATE DATABASE GameDB;
USE GameDB;

DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS users;

CREATE TABLE games (
    ID int NOT NULL auto_increment,
    name VARCHAR(50),
    platform VARCHAR(50),
    description VARCHAR(2000),
    PRIMARY KEY(ID)
);

CREATE TABLE users (
    fullname VARCHAR(50),
    password VARCHAR(50),
    emailid VARCHAR(100),
    PRIMARY KEY(emailid)
);

INSERT INTO games(name,platform,description) VALUE ('Kingdom Hearts','PlayStation 2','Kingdom Hearts is a series of action role-playing games developed and published by Square Enix.');

INSERT INTO users VALUE ('John Adam','ABCabc123$','john.adam@outlook.com');

Select * from games;

Select * from users;