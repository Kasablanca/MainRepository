package com.min.someapp.dao.model;

public class LoggingEventException {
    private Long eventId;

    private Short i;

    private String traceLine;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Short getI() {
        return i;
    }

    public void setI(Short i) {
        this.i = i;
    }

    public String getTraceLine() {
        return traceLine;
    }

    public void setTraceLine(String traceLine) {
        this.traceLine = traceLine == null ? null : traceLine.trim();
    }
}