package com.ntnu.wip.nabl.Models;

import android.content.Context;

import com.google.firebase.firestore.Exclude;
import com.ntnu.wip.nabl.Utils;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by klingen on 26.04.18.
 * This function holst the values that assigns a day of work
 * this class will contain logic for returning the data in a
 * friendly format.
 *
 * A TIMESHEET WILL HAVE ZERO TO MANY WorDay's
 */

public class WorkDay {
    private String id;
    private String userId;
    private String projectId;
    private String clientId;
    private DateTime startTime;
    private DateTime endTime;
    private float breakTime;
    private float overTime;
    private float holyDay;
    private float weekEnd;
    private String description;


    /**
     * Empty constructor, with auto generated id
     */
    public WorkDay() {
        id = Utils.generateUniqueId(24);
    }

    /**
     * Used in the case of the user inputting data from a former
     * day. Then the day must be added
     * @param start beginning of the day. ex 09:00
     * @param stop end of the day 17:00
     */
    public WorkDay (DateTime start, DateTime stop) {
        startTime = start;
        endTime = stop;

        id = Utils.generateUniqueId(24);
        breakTime = 0;
        holyDay = 0;
        weekEnd = 0;
        overTime = 0;
    }

    /**
     * Round to first begun half-hour
     * @return
     */
    public double getTotalHours() {

        Hours hours = Hours.hoursBetween(startTime, endTime);
        int wholeHours = hours.getHours();

        Minutes minutes = Minutes.minutesBetween(startTime, endTime);
        double restMinutes = (minutes.getMinutes()-wholeHours*60);

        double halfHour = 0;

        if (restMinutes > 10) {
            halfHour = 0.5;
        }

        if (restMinutes >= 40) {
            halfHour = 1.0;
        }


        //double halfHour = Math.round(restMinutes/30)/ 2.0;

        return wholeHours+halfHour;
    }

    // ################### SETTERS / GETTERS #####################

    // Date of the workday is decided by the first day of work
    @Exclude
    public Calendar getDay() {
        Calendar cal = new GregorianCalendar();
        if(startTime != null) {
            cal.set(Calendar.DAY_OF_MONTH, startTime.getDayOfMonth());
            cal.set(Calendar.MONTH, startTime.getMonthOfYear());
            cal.set(Calendar.YEAR, startTime.getYear());
            return cal;
        }
        return null;
    }

    public float getBreakTime() {
        return breakTime;
    }

    @Exclude
    public DateTime getEndTime() {
        return endTime;
    }

    public float getOverTime() {
        return overTime;
    }

    @Exclude
    public DateTime getStartTime() {
        return startTime;
    }

    public void setBreakTime(float breakTime) {
        this.breakTime = breakTime;
    }

    @Exclude
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public void setHolyDay(float holyDay) {
        this.holyDay = holyDay;
    }

    public void setOverTime(float overTime) {
        this.overTime = overTime;
    }

    @Exclude
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setWeekEnd(float weekEnd) {
        this.weekEnd = weekEnd;
    }

    public float getHolyDay() {
        return holyDay;
    }

    public float getWeekEnd() {
        return weekEnd;
    }

    // Hours - break
    public double getTotal() {
        return getTotalHours()-getBreakTime();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public long getStartTimeInMillis() {
        return startTime.getMillis();
    }

    public long getEndTimeInMillis() {
        return endTime.getMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getProjectId() {
        return projectId;
    }
}
