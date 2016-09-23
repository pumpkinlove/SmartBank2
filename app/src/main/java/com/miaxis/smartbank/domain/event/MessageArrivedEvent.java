package com.miaxis.smartbank.domain.event;

/**
 * Created by xu.nan on 2016/9/23.
 */
public class MessageArrivedEvent {

    String topic;
    String message;

    public MessageArrivedEvent() {
    }

    public MessageArrivedEvent(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
