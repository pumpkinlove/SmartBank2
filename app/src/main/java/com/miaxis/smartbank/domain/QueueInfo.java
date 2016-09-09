package com.miaxis.smartbank.domain;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/9/9.
 */
public class QueueInfo implements Serializable {

    private String typeName;
    private String firstChar;
    private String waitNum;
    private String curNum;
    private String nextNum;
    private String averageWaitTime;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getWaitNum() {
        return waitNum;
    }

    public void setWaitNum(String waitNum) {
        this.waitNum = waitNum;
    }

    public String getCurNum() {
        return curNum;
    }

    public void setCurNum(String curNum) {
        this.curNum = curNum;
    }

    public String getNextNum() {
        return nextNum;
    }

    public void setNextNum(String nextNum) {
        this.nextNum = nextNum;
    }

    public String getAverageWaitTime() {
        return averageWaitTime;
    }

    public void setAverageWaitTime(String averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }
}
