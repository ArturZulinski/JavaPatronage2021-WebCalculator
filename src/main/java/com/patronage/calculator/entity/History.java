package com.patronage.calculator.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class History {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "Identification number")
    private Long id;

    @Column(name = "time_stamp")
    @ApiModelProperty(notes = "Timestamp")
    private LocalDateTime timeOfLog;

    @Column(name = "logger")
    @ApiModelProperty(notes = "LOG")
    private String messageOfLog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfLog() {
        return timeOfLog;
    }

    public void setTimeOfLog(LocalDateTime timeOfLog) {
        this.timeOfLog = timeOfLog;
    }

    public String getMessageOfLog() {
        return messageOfLog;
    }

    public void setMessageOfLog(String messageOfLog) {
        this.messageOfLog = messageOfLog;
    }
}