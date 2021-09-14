SET client_encoding = 'UTF8';

CREATE TABLE users (
	id INT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
);

CREATE TABLE permission (
	id INT PRIMARY KEY,
	description VARCHAR(50) NOT NULL
);

CREATE TABLE users_permission (
	users_id INT NOT NULL,
	permission_id INT NOT NULL,
	PRIMARY KEY (users_id, permission_id),
	FOREIGN KEY (users_id) REFERENCES users(id),
	FOREIGN KEY (permission_id) REFERENCES permission(id)
);

INSERT INTO users (id, name, email, password) values (1, 'Admin', 'admin@domain.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO users (id, name, email, password) values (2, 'User', 'user@domain.com', '$2a$10$7ns33GDDAZ4LlBA3.jyDsOD3Ne5xLTRybRRVr1eYtn7MpCxmYTwY2');

INSERT INTO permission (id, description) values (1, 'ROLE_SAVE_CATEGORY');
INSERT INTO permission (id, description) values (2, 'ROLE_SEARCH_CATEGORY');

INSERT INTO permission (id, description) values (3, 'ROLE_SAVE_PERSON');
INSERT INTO permission (id, description) values (4, 'ROLE_DELETE_PERSON');
INSERT INTO permission (id, description) values (5, 'ROLE_SEARCH_PERSON');

INSERT INTO permission (id, description) values (6, 'ROLE_SAVE_ENTRY');
INSERT INTO permission (id, description) values (7, 'ROLE_DELETE_ENTRY');
INSERT INTO permission (id, description) values (8, 'ROLE_SEARCH_ENTRY');

INSERT INTO users_permission (users_id, permission_id) values (1, 1);
INSERT INTO users_permission (users_id, permission_id) values (1, 2);
INSERT INTO users_permission (users_id, permission_id) values (1, 3);
INSERT INTO users_permission (users_id, permission_id) values (1, 4);
INSERT INTO users_permission (users_id, permission_id) values (1, 5);
INSERT INTO users_permission (users_id, permission_id) values (1, 6);
INSERT INTO users_permission (users_id, permission_id) values (1, 7);
INSERT INTO users_permission (users_id, permission_id) values (1, 8);

INSERT INTO users_permission (users_id, permission_id) values (2, 2);
INSERT INTO users_permission (users_id, permission_id) values (2, 5);
INSERT INTO users_permission (users_id, permission_id) values (2, 8);
