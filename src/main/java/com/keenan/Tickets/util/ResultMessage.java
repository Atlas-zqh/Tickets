package com.keenan.Tickets.util;

/**
 * @author keenan on 28/01/2018
 */
public class ResultMessage {
    public static final int SUCCESS=0;
    public static final int ERROR=1;

    private String resultMessage;
    private int resultCode;

    public ResultMessage(int resultCode, String resultMessage){
        this.resultCode=resultCode;
        this.resultMessage=resultMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
