CREATE TABLE Tickets.ticket_order
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    orderPrice double,
    orderStatus varchar(255),
    orderTime datetime,
    payTime datetime,
    ticketOrderType varchar(255),
    showPlan_id bigint(20),
    user_id bigint(20),
    ticketNumber varchar(255),
    seatNum int(11),
    CONSTRAINT FKe81wsiprkldfpkryp05uii6fr FOREIGN KEY (showPlan_id) REFERENCES show_plan,
    CONSTRAINT FKs2k42y7sfqbo59fvds8l5gppw FOREIGN KEY (user_id) REFERENCES user
);
CREATE INDEX FKe81wsiprkldfpkryp05uii6fr ON Tickets.ticket_order (showPlan_id);
CREATE INDEX FKs2k42y7sfqbo59fvds8l5gppw ON Tickets.ticket_order (user_id);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (1, 672, 'INVALID_EXPIRED', '2018-03-29 09:16:21', null, 'CHOOSE_SEATS', 12, 1, '1522286180857', null);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (2, 1083, 'INVALID_EXPIRED', '2018-03-29 10:27:08', null, 'CHOOSE_SEATS', 12, 1, '1522289287791', null);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (3, 560, 'INVALID_EXPIRED', '2018-03-29 11:47:46', null, 'CHOOSE_SEATS', 12, 1, '1522295265661', null);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (4, 560, 'COMPLETED', '2018-03-29 11:48:21', '2018-03-29 08:14:18', 'CHOOSE_SEATS', 12, 1, '1522295300816', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (5, 560, 'INVALID_EXPIRED', '2018-03-29 11:48:31', null, 'CHOOSE_SEATS', 12, 1, '1522295310565', null);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (6, 840, 'INVALID_EXPIRED', '2018-03-29 11:50:36', null, 'CHOOSE_SEATS', 12, 1, '1522295436491', null);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (7, 840, 'INVALID_EXPIRED', '2018-03-29 11:51:54', null, 'CHOOSE_SEATS', 12, 1, '1522295514192', null);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (8, 1064, 'INVALID_REFUND', '2018-03-29 16:06:14', '2018-03-29 16:07:42', 'CHOOSE_SEATS', 12, 1, '1522310774341', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (9, 1444, 'INVALID_REFUND', '2018-03-29 16:09:04', '2018-03-29 16:09:13', 'CHOOSE_SEATS', 12, 1, '1522310944039', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (10, 2508, 'INVALID_EXPIRED', '2018-03-29 16:09:33', null, 'CHOOSE_SEATS', 12, 1, '1522310972838', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (11, 1140, 'INVALID_CANCELED', '2018-03-29 20:34:31', null, 'CHOOSE_SEATS', 12, 1, '1522326870534', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (12, 3960, 'COMPLETED', '2018-03-30 16:08:07', '2018-03-30 16:08:16', 'CHOOSE_SEATS', 12, 1, '1522397286943', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (13, 448.2, 'SUCCESS_PAID', '2018-04-01 10:54:34', '2018-04-01 10:55:51', 'IMMEDIATE', 14, 1, '1522551273818', 6);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (14, 2176, 'SUCCESS_PAID', '2018-04-01 11:19:58', '2018-04-01 11:20:07', 'CHOOSE_SEATS', 21, 1, '1522552797881', 0);
INSERT INTO Tickets.ticket_order (id, orderPrice, orderStatus, orderTime, payTime, ticketOrderType, showPlan_id, user_id, ticketNumber, seatNum) VALUES (15, 3760, 'SUCCESS_UNPAID', '2018-04-01 17:16:17', null, 'CHOOSE_SEATS', 20, 1, '1522574176926', 0);