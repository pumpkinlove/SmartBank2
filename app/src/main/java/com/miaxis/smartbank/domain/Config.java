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

    @Column(name = "emqttIp")
    private String emqttIp;

    @Column(name = "emqttPort")
    private String emqttPort;

    @Column(name = "clientId")
    private String clientId;

    @Column(name = "organid")
    private String organid;

    @Column(name = "organname")
    private String organname;

    private String url;

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

    public String getEmqttIp() {
        return emqttIp;
    }

    public void setEmqttIp(String emqttIp) {
        this.emqttIp = emqttIp;
    }

    public String getEmqttPort() {
        return emqttPort;
    }

    public void setEmqttPort(String emqttPort) {
        this.emqttPort = emqttPort;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getOrganname() {
        return organname;
    }

    public void setOrganname(String organname) {
        this.organname = organname;
    }

    public String getUrl() {
        return "http://" + ip + ":" + port;
    }

}
