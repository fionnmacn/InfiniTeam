package com.example.prototype;

import java.io.Serializable;

public class NoticeModel implements Serializable {

    private String id;
    private String subject;
    private String content;
    private boolean priority;

    public NoticeModel(String id, String subject, String content, boolean priority) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
