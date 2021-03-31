package com.example.prototype;

import java.io.Serializable;

public class TaskModel  implements Serializable {

    String id;
    String description;
    boolean priority;
    String createdAt;

    public TaskModel(String id, String description, boolean priority, String createdAt) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
