package com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.ArrayAdapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface that connect between LoggingInputView (view) and it's Controller LoggingInputController
 * Created by a7med on 29.04.18.
 */
public interface ILoggingInputView extends IAbstractMvcView {
    // Functions to implement in controller
    interface LoggingInputListener {
        void dateFieldPressed();
        void invalidDateSupplied();
        void timeFieldPressed();
        void overTimePressed();
    }

    // Functions to implement in View
    void registerExportListener(LoggingInputListener listener);
    void setBreakSpinnerData(ArrayAdapter<String> adapter);
    void setOverTimeSpinnerData(ArrayAdapter<String> adapter);
    void setDateDialog(DatePickerDialog dialog);
    void setDateOnFields(String date);
    void setTimeDialog(TimePickerDialog dialog);
    void setTimeOnFields(String nowTime);
    void enableOverTimeBox();
}