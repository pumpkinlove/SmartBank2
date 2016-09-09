package com.miaxis.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by xu.nan on 2016/8/19.
 */
@Table(name = "config")
public class Config {

    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "ip")
    private String ip;
    @Column(name = "port")
    private String port;

    public Config() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
