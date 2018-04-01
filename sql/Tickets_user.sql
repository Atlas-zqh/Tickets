CREATE TABLE Tickets.user
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(255),
    isConfirmed bit(1),
    isValid bit(1),
    points double,
    username varchar(255),
    balance double,
    level int(11),
    password varchar(255),
    code varchar(255),
    role_id bigint(20),
    levelCoupon_id bigint(20),
    loginCode varchar(255),
    bankAccount varchar(255),
    bankPassword varchar(255),
    CONSTRAINT FK60qlg9oata44io3a80yh31536 FOREIGN KEY (role_id) REFERENCES roles,
    CONSTRAINT FKeswup70mfp7kgq5qja4ip8dcl FOREIGN KEY (levelCoupon_id) REFERENCES level_coupon
);
CREATE INDEX FK60qlg9oata44io3a80yh31536 ON Tickets.user (role_id);
CREATE INDEX FKeswup70mfp7kgq5qja4ip8dcl ON Tickets.user (levelCoupon_id);
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (1, '151250206@smail.nju.edu.cn', true, true, 8760, 'zqh', 90907.8, 3, '123456', null, 2, 3, null, '12345678', '12345678');
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (9, 'admin@jnjy.com', true, true, 0, '0000007', 0, 1, '123456', '', 3, null, null, null, null);
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (10, 'admin@oula', true, true, 0, '0000002', 0, 1, '123456', null, 3, null, null, null, null);
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (11, 'admin@ticket.com', true, true, null, 'admin', null, null, 'admin', null, 1, null, null, null, null);
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (12, 'msds@msds.com', true, true, 0, '0000008', 0, 1, '123456', '', 3, null, null, null, null);
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (13, 'shdjy@shdjy.com', true, true, 0, '0000009', 0, 1, '123456', '', 3, null, null, null, null);
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (15, '931859758@qq.com', true, true, 0, 'test', 100000, 1, '123456', '2540c4d7bcc549548a777d06e367f72c', 2, null, null, '12345678', '12345678');
INSERT INTO Tickets.user (id, email, isConfirmed, isValid, points, username, balance, level, password, code, role_id, levelCoupon_id, loginCode, bankAccount, bankPassword) VALUES (16, 'east@east.com', false, true, 0, '0000010', 0, 1, '123456', '', 3, null, null, null, null);