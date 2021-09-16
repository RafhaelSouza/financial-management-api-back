SET client_encoding = 'UTF8';

CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO category(name) values ('Leisure');
INSERT INTO category(name) values ('Food');
INSERT INTO category(name) values ('Groceries');
INSERT INTO category(name) values ('Health');
INSERT INTO category(name) values ('Others');