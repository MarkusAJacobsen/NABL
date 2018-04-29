package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput.ILoggingInputView;
import com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput.LoggingInputView;
import com.ntnu.wip.nabl.R;

import java.util.Calendar;

/**
 * Class that represent the Log Activity
 * Created by a7med on 29.04.18.
 */
public class LoggingInputController extends AppCompatActivity implements
        ILoggingInputView.LoggingInputListener {
    private LoggingInputView mvcView;

    /**
     * Android Activity Life Cycle function, runs whens the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mvcView = new LoggingInputView(getLayoutInflater(), null);
        this.mvcView.registerExportListener(this);
        this.mvcView.setActionBar(getSupportActionBar());
        this.mvcView.setActionBarTitle(getString(R.string.newLogging));

        fetchDateAndTimeFields();
        fetchSpinnersData();

        setContentView(this.mvcView.getRootView());
    }

    /**
     * Function to fetch the Date and Time once the activity is created
     */
    private void fetchDateAndTimeFields() {
        final Calendar date = Calendar.getInstance();
        final int day = date.get(date.DAY_OF_MONTH);
        final int month = date.get(date.MONTH);
        final int year = date.get(date.YEAR);
        final int hour = date.get(date.HOUR_OF_DAY);
        final int min = date.get(date.MINUTE);

        final String today = day + "." + month + "." + year;
        final String now = hour + ":" + min;

        fetchDateFields(today);
        fetchTimeFields(now);
    }

    /**
     * Add the time to the fields
     * @param nowTime
     */
    private void fetchTimeFields(String nowTime) {
        this.mvcView.setTimeOnFields(nowTime);
    }

    /**
     * Add the date to the fields
     * @param today
     */
    private void fetchDateFields(String today) {
        this.mvcView.setDateOnFields(today);
    }

    /**
     * Function to manage the spinners
     */
    private void fetchSpinnersData() {
        fetchBreakSpinnerData();
        fetchOverTimeSpinnerData();
    }

    /**
     * Add data from resources to the over time spinner
     */
    private void fetchOverTimeSpinnerData() {
        final String[] overTimeData = getResources().getStringArray(R.array.overTiming);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, overTimeData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mvcView.setOverTimeSpinnerData(adapter);
    }

    /**
     * Add data from resources to the break Spinner
     */
    private void fetchBreakSpinnerData() {
        final String[] breakDate = getResources().getStringArray(R.array.breakTiming);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, breakDate);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mvcView.setBreakSpinnerData(adapter);
    }

    @Override
    public void dateFieldPressed() {
        DatePickerDialog dateDialog = new DatePickerDialog(LoggingInputController.this);
        this.mvcView.setDateDialog(dateDialog);
    }

    @Override
    public void invalidDateSupplied() {
        Toast.makeText(getApplicationContext(), getString(R.string.invalidDate),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void timeFieldPressed() {
        final Calendar date = Calendar.getInstance();

        TimePickerDialog timeDialog = new TimePickerDialog(LoggingInputController.this,
                this.mvcView, date.get(date.HOUR_OF_DAY), date.get(date.MINUTE), true);
        this.mvcView.setTimeDialog(timeDialog);
    }

    @Override
    public void overTimePressed() {
        this.mvcView.enableOverTimeBox();
    }
}
