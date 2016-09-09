package com.miaxis.smartbank.domain;

import java.util.Date;

/**
 * Created by xu.nan on 2016/8/17.
 */
public class Version {
    private int id;
    private int versionCode;
    private String versionName;
    private Date opdate;
    private String descript;
    private String path;

    public Version() {
    }

    public Version(String versionName, int versionCode) {
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getOpdate() {
        return opdate;
    }

    public void setOpdate(Date opdate) {
        this.opdate = opdate;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
