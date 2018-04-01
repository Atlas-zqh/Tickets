CREATE TABLE Tickets.venue_permission
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    createTime datetime,
    status varchar(255),
    venue_id bigint(20),
    type varchar(255),
    modifyContent varchar(255),
    CONSTRAINT FK7pl6c5hjq4elctpxfmf6olv10 FOREIGN KEY (venue_id) REFERENCES venue
);
CREATE INDEX FK7pl6c5hjq4elctpxfmf6olv10 ON Tickets.venue_permission (venue_id);
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (1, '2018-03-30 10:03:08', 'PASS', 7, 'VENUE_ADDRESS', '南京市延陵巷5号');
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (2, '2018-03-30 10:42:52', 'PASS', 7, 'VENUE_ADDRESS', '江苏省南京市延陵巷5号');
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (3, '2018-03-30 10:44:57', 'PASS', 7, 'VENUE_ADDRESS', '南京市延陵巷5号');
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (4, '2018-04-01 10:11:30', 'PASS', 8, 'VENUE_REGISTER', null);
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (5, '2018-04-01 10:16:21', 'PASS', 9, 'VENUE_REGISTER', null);
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (6, '2018-04-01 10:17:15', 'PASS', 9, 'VENUE_ADDRESS', '上海黄浦区人民大道300号');
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (7, '2018-04-01 10:35:12', 'PASS', 8, 'VENUE_ADDRESS', '上海浦东新区世博大道1200号');
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (8, '2018-04-01 17:22:46', 'WAIT_LIST', 10, 'VENUE_REGISTER', null);
INSERT INTO Tickets.venue_permission (id, createTime, status, venue_id, type, modifyContent) VALUES (9, '2018-04-01 17:23:30', 'WAIT_LIST', 9, 'VENUE_ADDRESS', '上海市黄浦区人民大道300号');