package com.miaxis.smartbank.domain;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/10/10.
 */
public class Production implements Serializable {

    private int id;
    private String name;
    private String path;
    private String remark;          //描述
    private String term;            //营销术语
    private String opdate;
    private String optime;
    private String organid;
    private String organname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getOpdate() {
        return opdate;
    }

    public void setOpdate(String opdate) {
        this.opdate = opdate;
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime;
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
}
