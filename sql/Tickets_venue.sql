CREATE TABLE Tickets.venue
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    address varchar(255),
    name varchar(255),
    password varchar(255),
    venueId varchar(255),
    editPermit bit(1)
);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (1, '南京市广州路177号', '南京五台山体育场', '123456', '0000001', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (2, '南京市玄武区阳光路3号太阳宫演艺广场负一层', '欧拉艺术空间', '123456', '0000002', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (3, '江苏省南京市太平门阳光路3号', '南京太阳宫', '123456', '0000003', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (4, '南京市建邺区江东中路222号奥体中心东门', '南京奥体中心体育馆', '123456', '0000004', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (5, '江苏省南京市建邺区梦都大街181号', '江苏大剧院', '123456', '0000005', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (6, '河西新区金沙江西街与燕山路交叉口', '南京保利大剧院', '123456', '0000006', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (7, '南京市延陵巷5号', '江苏省江南剧院', '123456', '0000007', true);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (8, '上海浦东新区世博大道1200号', '上海梅赛德斯奔驰文化中心', '123456', '0000008', true);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (9, '上海黄浦区人民大道300号', '上海大剧院', '123456', '0000009', false);
INSERT INTO Tickets.venue (id, address, name, password, venueId, editPermit) VALUES (10, null, '东方艺术中心', '123456', '0000010', true);