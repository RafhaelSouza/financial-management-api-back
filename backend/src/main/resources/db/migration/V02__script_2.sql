SET client_encoding = 'UTF8';

CREATE TABLE IF NOT EXISTS person (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address_street VARCHAR(100),
    address_number VARCHAR(10),
    address_complement VARCHAR(100),
    address_district VARCHAR(100),
    address_postalcode VARCHAR(15),
    address_city VARCHAR(50),
    address_state VARCHAR(50),
    active BOOLEAN NOT NULL
);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Juan Severino Lucca Cardoso', 'Rua Marilice Rodrigues da Silva Pinto',
        '375', 'Sala B', 'Paulicéia', '13401-545', 'Piracicaba', 'SP', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Márcio Anthony Melo', 'Rua Santana', '384',
         null, 'Boca da Mata', '65917-155', 'Imperatriz', 'MA', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Joaquim Erick Cauã Barbosa', 'Quadra 704 Sul Alameda 2', '776',
        'Apto 54', 'Plano Diretor Sul', '77022-332', 'Palmas', 'TO', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Sophie Hadassa Sebastiana Alves', 'Rua Salmão', '998', null,
        'Loteamento Vila Azul', '77815-826', 'Araguaína', 'TO', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Aline Elza Nunes', 'Rua Ernesto Alves', '541', 'Apto C',
        'Centro', '96810-188', 'Santa Cruz do Sul', 'RS', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Débora Sabrina da Conceição', 'Beco Santana', '853', null,
	    'Nova Gameleira', '30510-243', 'Belo Horizonte', 'MG', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Lavínia Letícia Sales', 'Avenida Nazaré Filgueiras', '125',
        '2° andar', 'Doutor Sílvio Botelho', '69314-550', 'Boa Vista', 'RR', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Pedro Emanuel Pereira', 'Rua Tirso Leal', '103', null, 'Maraponga',
        '60711-100', 'Fortaleza', 'CE', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Marcos Vicente Assunção', 'Rua A', '426', '3° andar',
        'Aeroporto', '49037-699', 'Aracaju', 'SE', TRUE);

INSERT INTO person (name, address_street, address_number, address_complement, address_district, address_postalcode, address_city, address_state, active)
VALUES ('Fábio Noah Lima', 'Travessa Zacarias', '125', null,
        'Ayrton Sena', '69911-836', 'Rio Branco', 'AC', TRUE);
