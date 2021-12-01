CREATE TABLE contact (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    person_id INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	telephone VARCHAR(20) NOT NULL,
  FOREIGN KEY (person_id) REFERENCES person(id)
);

INSERT INTO contact (id, person_id, name, email, telephone) values (1, 1, 'Someone Somebody', 'rafhael.dev@gmail.com', '99 9999-9999');
