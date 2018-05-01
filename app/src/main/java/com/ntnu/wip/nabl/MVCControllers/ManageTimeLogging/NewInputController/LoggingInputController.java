package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging.NewInputController;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput.ILoggingInputView;
import com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput.LoggingInputView;
import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.R;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Class that represent the Log Activity
 * Created by a7med on 29.04.18.
 */
public class LoggingInputController extends AppCompatActivity implements
        ILoggingInputView.LoggingInputListener {
    private static final int THIRTY_MIN = 30;           // 30 min break
    private static final float MIN_IN_HOUR = 60f;       // 60 min in 1 hour

    private LoggingInputView mvcView;
    private String logType;
    private String objectID;
    private float breaks;                           // Breaks in Minute
    private float overTime;                         // Overtime in Minute
    private boolean overTimePressed = false;

    /**
     * Android Activity Life Cycle function, runs whens the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchIntentData();

        this.mvcView = new LoggingInputView(getLayoutInflater(), null);
        this.mvcView.registerExportListener(this);
        this.mvcView.setActionBar(getSupportActionBar());
        this.mvcView.setActionBarTitle(getString(R.string.newLogging));

        fetchDateAndTimeFields();
        fetchSpinnersData();

        setContentView(this.mvcView.getRootView());
    }

    /**
     * Function to fetch data from Intent, sent from previous activity
     */
    private void fetchIntentData() {
        Intent receivedIntent = getIntent();
        logType = receivedIntent.getStringExtra(getString(R.string.type));
        objectID = receivedIntent.getStringExtra(getString(R.string.id));
    }

    /**
     * Function to get current user ID
     * @return User ID
     */
    private String getUserID() {
        return new FirestoreAuthentication().getUId();
    }

    /**
     * Function to fetch the Date and Time once the activity is created
     */
    private void fetchDateAndTimeFields() {
        final Calendar date = Calendar.getInstance();
        final int day = date.get(Calendar.DAY_OF_MONTH);
        final int month = date.get(Calendar.MONTH) + 1;         // Because MONTH is 0-based array
        final int year = date.get(Calendar.YEAR);
        final int hour = date.get(Calendar.HOUR_OF_DAY);
        final int min = date.get(Calendar.MINUTE);


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

    /**
     * Function to register A Log
     */
    private void registerALog() {
        FireStoreClient client = new FireStoreClient(this);

        WorkDay day = generateWorkingDay();
        client.newLogEntry(day);
    }

    /**
     * Function to generate a @WorkingDay Object
     * @return
     */
    private WorkDay generateWorkingDay() {
        WorkDay day = new WorkDay(new DateTime(mvcView.getStartDate()),
                new DateTime(mvcView.getEndDate()));

        day.setUserId(getUserID());

        if (logType == getString(R.string.project)){
            day.setProjectId(objectID);
        } else {
            day.setClientId(objectID);
        }

        day.setBreakTime(breaks);
        day.setOverTime(overTime);
        day.setDescription(mvcView.getDescription());

        if(overTimePressed){
            float x = mvcView.getHoliday();
            float y = mvcView.getWeekend();
            day.setHolyDay(mvcView.getHoliday());
            day.setWeekEnd(mvcView.getWeekend());
        }

        return day;
    }

    /**
     * Function to check if the given time or date is invalid
     * @return
     */
    private boolean checkInvalidDateOrTime() {
        boolean somethingMissing = false;
        if (mvcView.checkTimeInvalid()) {
            Toast.makeText(getApplicationContext(), getString(R.string.invalidTime),
                    Toast.LENGTH_SHORT).show();
            somethingMissing = true;
        }
        if (mvcView.checkDateInvalid()) {
            Toast.makeText(getApplicationContext(), getString(R.string.invalidDate),
                    Toast.LENGTH_SHORT).show();
            somethingMissing = true;
        }
        return somethingMissing;
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
    public void invalidTimeSupplied() {
        Toast.makeText(getApplicationContext(), getString(R.string.invalidTime),
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
        if(overTimePressed) {
            overTimePressed = false;
        }
        else {
            overTimePressed = true;
        }
    }

    @Override
    public void updateBreaksCount(int position) {
        breaks = (position * THIRTY_MIN) / MIN_IN_HOUR;
    }

    @Override
    public void updateOverTimeCount(int position) {
        overTime = position * THIRTY_MIN / MIN_IN_HOUR;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button) {
            if (!checkInvalidDateOrTime()) {            // Valid date and time
                registerALog();
                onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        } else {
            onBackPressed();
            return true;
        }
    }
}