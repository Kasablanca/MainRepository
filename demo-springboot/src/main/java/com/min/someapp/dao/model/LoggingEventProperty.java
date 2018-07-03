package com.min.someapp.dao.model;

public class LoggingEventProperty {
    private Long eventId;

    private String mappedKey;

    private String mappedValue;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getMappedKey() {
        return mappedKey;
    }

    public void setMappedKey(String mappedKey) {
        this.mappedKey = mappedKey == null ? null : mappedKey.trim();
    }

    public String getMappedValue() {
        return mappedValue;
    }

    public void setMappedValue(String mappedValue) {
        this.mappedValue = mappedValue == null ? null : mappedValue.trim();
    }
}