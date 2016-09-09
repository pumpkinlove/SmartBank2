package com.miaxis.smartbank.domain;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/9/9.
 */
public class WindowInfo implements Serializable {

    private String windowNo;
    private String workerName;
    private String busiType;
    private String good;
    private String bad;
    private String status;
    private String averageBusiTime;             //平均办理时长

    public String getWindowNo() {
        return windowNo;
    }

    public void setWindowNo(String windowNo) {
        this.windowNo = windowNo;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAverageBusiTime() {
        return averageBusiTime;
    }

    public void setAverageBusiTime(String averageBusiTime) {
        this.averageBusiTime = averageBusiTime;
    }
}
