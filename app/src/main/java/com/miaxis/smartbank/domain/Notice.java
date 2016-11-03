package com.miaxis.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by xu.nan on 2016/9/8.
 */
@Table(name = "notice")
public class Notice {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "isRead")
    private boolean isRead;
    @Column(name = "type")
    private int type;
    @Column(name = "opdate")
    private String opdate;
    @Column(name = "optime")
    private String optime;
    @Column(name = "operator")
    private String operator;
    @Column(name = "terminalId")
    private String terminalId;
    @Column(name = "terminalType")
    private String terminalType;
    @Column(name =  "source")
    private String source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
