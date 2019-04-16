package com.teamapple.models;

import java.util.Date;

public class Notification {
    private String label;
    private String description;
    private Date date;
    private Date startTime;
    private Date endTime;

    public Notification(String label, String description, Date date, Date startTime, Date endTime) {
        this.label = label;
        this.description = description;
        this.date=date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
