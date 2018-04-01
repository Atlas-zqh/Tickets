CREATE TABLE Tickets.user_coupon
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    used bit(1),
    coupon_id bigint(20),
    user_id bigint(20),
    CONSTRAINT FK23vpkw483hhbe77dgvimcipf4 FOREIGN KEY (coupon_id) REFERENCES coupon,
    CONSTRAINT FKhu3g6gb02g6toj7gedrhos8bw FOREIGN KEY (user_id) REFERENCES user
);
CREATE INDEX FK23vpkw483hhbe77dgvimcipf4 ON Tickets.user_coupon (coupon_id);
CREATE INDEX FKhu3g6gb02g6toj7gedrhos8bw ON Tickets.user_coupon (user_id);
INSERT INTO Tickets.user_coupon (id, used, coupon_id, user_id) VALUES (1, true, 1, 1);
INSERT INTO Tickets.user_coupon (id, used, coupon_id, user_id) VALUES (2, false, 2, 1);
INSERT INTO Tickets.user_coupon (id, used, coupon_id, user_id) VALUES (3, true, 1, 1);
INSERT INTO Tickets.user_coupon (id, used, coupon_id, user_id) VALUES (4, true, 2, 1);
INSERT INTO Tickets.user_coupon (id, used, coupon_id, user_id) VALUES (5, false, 1, 1);
INSERT INTO Tickets.user_coupon (id, used, coupon_id, user_id) VALUES (6, false, 2, 1);