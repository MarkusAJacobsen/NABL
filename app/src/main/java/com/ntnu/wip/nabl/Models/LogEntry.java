package com.ntnu.wip.nabl.Models;

import java.util.Date;

@Deprecated // Replaced by WorkDay.java
public class LogEntry {
    private String id;
    private Date entryDate;
    private int startTime;
    private int finishTime;
    private int lunchInMinutes;
    private int totalWorkingTime;
    private String description;

    public LogEntry(){
        //No-OP
    }

    public LogEntry(String id, Date entryDate, int startTime, int finishTime, int lunchInMinutes, int totalWorkingTime, String description) {
        this.id = id;
        this.entryDate = entryDate;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.lunchInMinutes = lunchInMinutes;
        this.totalWorkingTime = totalWorkingTime;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getLunchInMinutes() {
        return lunchInMinutes;
    }

    public void setLunchInMinutes(int lunchInMinutes) {
        this.lunchInMinutes = lunchInMinutes;
    }

    public int getTotalWorkingTime() {
        return totalWorkingTime;
    }

    public void setTotalWorkingTime(int totalWorkingTime) {
        this.totalWorkingTime = totalWorkingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
