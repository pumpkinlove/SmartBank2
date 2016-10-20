package com.miaxis.smartbank.domain.event;

/**
 * Created by xu.nan on 2016/10/20.
 */

public class NetStatusEvent {

    private String netStatus;
    private boolean connectFlag;

    public NetStatusEvent() {
    }

    public NetStatusEvent(String netStatus, boolean connectFlag) {
        this.netStatus = netStatus;
        this.connectFlag = connectFlag;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public boolean isConnectFlag() {
        return connectFlag;
    }

    public void setConnectFlag(boolean connectFlag) {
        this.connectFlag = connectFlag;
    }
}
