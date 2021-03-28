package com.example.prototype;

import java.io.Serializable;

public class NoticeModel implements Serializable {

    private String id;
    private String subject;
    private String content;

    public NoticeModel(String id, String subject, String content) {
        this.id = id;
        this.subject = subject;
        this.content = content;
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
}
