CREATE TABLE Tickets.level_coupon
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    couponName varchar(255),
    discount double,
    fitLevel int(11)
);
INSERT INTO Tickets.level_coupon (id, couponName, discount, fitLevel) VALUES (1, '会员1级优惠', 0.95, 1);
INSERT INTO Tickets.level_coupon (id, couponName, discount, fitLevel) VALUES (2, '会员2级优惠', 0.9, 2);
INSERT INTO Tickets.level_coupon (id, couponName, discount, fitLevel) VALUES (3, '会员3级优惠', 0.85, 3);
INSERT INTO Tickets.level_coupon (id, couponName, discount, fitLevel) VALUES (4, '会员4级优惠', 0.8, 4);
INSERT INTO Tickets.level_coupon (id, couponName, discount, fitLevel) VALUES (5, '会员5级优惠', 0.75, 5);