CREATE TABLE Tickets.coupon
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    couponName varchar(255),
    discount double,
    expireTime datetime,
    needPoints double
);
INSERT INTO Tickets.coupon (id, couponName, discount, expireTime, needPoints) VALUES (1, '春季八折优惠券优惠', 0.8, '2018-07-01 11:30:10', 100);
INSERT INTO Tickets.coupon (id, couponName, discount, expireTime, needPoints) VALUES (2, '2018全年九折优惠', 0.9, '2019-01-01 11:41:22', 500);