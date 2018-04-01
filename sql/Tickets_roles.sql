CREATE TABLE Tickets.roles
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name varchar(255)
);
INSERT INTO Tickets.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO Tickets.roles (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO Tickets.roles (id, name) VALUES (3, 'ROLE_VENUE');