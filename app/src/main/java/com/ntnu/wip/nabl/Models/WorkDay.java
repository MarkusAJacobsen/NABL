package com.ntnu.wip.nabl.Models;

import java.sql.Time;
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
    Time startTime;
    Time endTime;
    Calendar day;

    /**
     * Used in the case of the user inputting data from a former
     * day. Then the day must be added
     * @param start beginning of the day. ex 09:00
     * @param end end of the day 17:00
     * @param cal the date. The time of the object will be ignored
     */
    WorkDay (Time start, Time end, Calendar cal) {
        startTime = start;
        endTime = end;
        day = cal;
    }

    /**
     * Used for registering hours the same day as the work
     * was performed
     * @param start see top constructor
     * @param end see top constructor
     */
    WorkDay (Time start, Time end) {
        this.startTime = start;
        this.endTime = end;
        day = GregorianCalendar.getInstance();
    }

    /**
     * NOT TO BE USED
     */
    private WorkDay() {}
}
