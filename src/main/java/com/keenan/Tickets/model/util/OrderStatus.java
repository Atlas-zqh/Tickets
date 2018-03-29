package com.keenan.Tickets.model.util;

/**
 * @author keenan on 20/03/2018
 */
public enum OrderStatus {
    SUCCESS_UNPAID("未支付"), SUCCESS_PAID("已付款"), INVALID_EXPIRED("已失效"), INVALID_REFUND("已退款"), COMPLETED("已完成"), FAILED_ALLOCATE("已失效"), INVALID_CANCELED("已失效");

    private String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return text;
    }


}
