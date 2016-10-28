package com.miaxis.smartbank.domain.event;

/**
 * Created by xu.nan on 2016/10/28.
 */

public class NotifyEvent {

    private String topic;
    private String content;

    public NotifyEvent() {
    }

    public NotifyEvent(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
