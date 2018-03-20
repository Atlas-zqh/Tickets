package com.keenan.Tickets.util;

import java.util.Objects;

/**
 * @author keenan on 28/01/2018
 */
public class ResultMessage {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String resultMessage;
    private String resultCode;

    public ResultMessage(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ResultMessage(String resultCode) {
        this.resultCode = resultCode;
        this.resultMessage = "";
    }

    public ResultMessage() {
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultMessage)) return false;
        ResultMessage that = (ResultMessage) o;
        return Objects.equals(resultCode, that.resultCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(resultCode);
    }
}
