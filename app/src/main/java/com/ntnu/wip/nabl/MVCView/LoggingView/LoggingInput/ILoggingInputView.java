package com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

import java.util.Date;

/**
 * Interface that connect between LoggingInputView (view) and it's Controller LoggingInputController
 * Created by a7med on 29.04.18.
 */
public interface ILoggingInputView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface LoggingInputListener {
        /**
         * Date field pressed
         */
        void dateFieldPressed();

        /**
         * User entered invalid dates
         */
        void invalidDateSupplied();

        /**
         * User entered invalid times
         */
        void invalidTimeSupplied();

        /**
         * Time field pressed
         */
        void timeFieldPressed();

        /**
         * User pressed overtime
         */
        void overTimePressed();

        /**
         * Update breaks
         * @param position int
         */
        void updateBreaksCount(int position);

        /**
         * Update overtime
         * @param position int
         */
        void updateOverTimeCount(int position);
    }

     /**
     * Register a listener
     * Will enable an implementing class to invoke
     * functions in the listener.
     * @param listener LoggingInputListener
     */
    void registerExportListener(LoggingInputListener listener);

    /**
     * Set break spinner adapter
     * @param adapter Adapter
     */
    void setBreakSpinnerData(Adapter adapter);

    /**
     * Set overtime spinner adapter
     * @param adapter Adapter
     */
    void setOverTimeSpinnerData(Adapter adapter);

    /**
     * Set date dialog
     * @param dialog DatePickerDialog
     */
    void setDateDialog(DatePickerDialog dialog);

    /**
     * Set date field starting date
     * @param date String
     */
    void setDateOnFields(String date);

    /**
     * Set time dialog
     * @param dialog TimePickerDialog
     */
    void setTimeDialog(TimePickerDialog dialog);

    /**
     * Set time field starting time
     * @param nowTime String
     */
    void setTimeOnFields(String nowTime);

    /**
     * Enable the overtime box
     */
    void enableOverTimeBox();

    /**
     * Check if inputted date is valid
     * True if valid, false if not
     * @return boolean
     */
    boolean checkDateInvalid();

    /**
     * Check if inputted time is valid
     * True if valid, false if not
     * @return boolean
     */
    boolean checkTimeInvalid();

    /**
     * Get entered start date
     * @return Date
     */
    Date getStartDate();

    /**
     * Get entered end date
     * @return Date
     */
    Date getEndDate();

    /**
     * Get text from field 'Description'
     * @return String
     */
    String getDescription();

    /**
     * Get Holiday time
     * @return float
     */
    float getHoliday();

    /**
     * Get weekend time
     * @return float
     */
    float getWeekend();
}
