package com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ntnu.wip.nabl.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class that represent the View of registering a new Log
 * Created by a7med on 29.04.18.
 */
public class LoggingInputView implements ILoggingInputView, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private View rootView;
    private LoggingInputListener listener;
    private ActionBar actionBar;

    @BindView(R.id.startingDate) TextView startingDate;
    @BindView(R.id.endingDate) TextView endingDate;
    @BindView(R.id.startingTime) TextView startingTime;
    @BindView(R.id.endingTime) TextView endingTime;
    @BindView(R.id.weekendHours) TextView weekendHours;
    @BindView(R.id.holidayHours) TextView holidayHours;
    @BindView(R.id.breaksCounter) Spinner breakSpinner;
    @BindView(R.id.overTimeCounter) Spinner overTimeSpinner;
    @BindView(R.id.checkBox) CheckBox overTimeCheckBox;
    @BindView(R.id.extraHours) ConstraintLayout overTimeLayout;
    @BindView(R.id.description) TextView description;

    private DatePickerDialog dateDialog;
    private TimePickerDialog timeDialog;

    private Date sDate;
    private Date eDate;
    private boolean invalidDate = false;                // True if invalid, False if correct
    private boolean invalidTime = false;                // True if invalid, False if correct

    private LoggingInputView.WhichDate whichDate = LoggingInputView.WhichDate.NULL;
    private LoggingInputView.WhichTime whichTime = LoggingInputView.WhichTime.NULL;

    private enum WhichDate {
        START,
        END,
        NULL
    }

    private enum WhichTime {
        START, END, NULL
    }

    /**
     * Constructor to create and initialize the view
     * @param inflater
     * @param container
     */
    public LoggingInputView(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.logging_input, container);
        ButterKnife.bind(this, rootView);

        updateDateVariables();

        configureDatePickers();
        configureStartAndEndTime();

        configureViewSpinners();

        configureCheckBox();
    }

    private void configureViewSpinners() {
        breakSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (listener != null){
                    listener.updateBreaksCount(pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // NO OP
            }
        });

        overTimeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (listener != null){
                    listener.updateOverTimeCount(pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // NO OP
            }
        });
    }

    /**
     * Function to update date variables at the begging to now time
     */
    private void updateDateVariables() {
        Calendar cal = Calendar.getInstance();
        sDate = eDate = cal.getTime();
    }

    /**
     * Function to configure the CheckBox on clicking
     */
    private void configureCheckBox() {
        this.overTimeCheckBox.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.overTimePressed();
            }
        });
    }

    /**
     * Function to configure the time dialog on clicking Time fields
     */
    private void configureStartAndEndTime() {
        this.startingTime.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.timeFieldPressed();
                if (timeDialog != null) {
                    this.whichTime = WhichTime.START;
                    this.timeDialog.show();
                }
            }
        });

        this.endingTime.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.timeFieldPressed();
                if (timeDialog != null) {
                    this.whichTime = WhichTime.END;
                    this.timeDialog.show();
                }
            }
        });
    }

    /**
     * Function to configure the Date dialog on clicking Date fields
     */
    private void configureDatePickers() {
        this.startingDate.setOnClickListener(view -> {
            if(listener != null) {
                this.listener.dateFieldPressed();
                if(dateDialog != null) {
                    whichDate = LoggingInputView.WhichDate.START;
                    this.dateDialog.show();
                }
            }
        });

        this.endingDate.setOnClickListener(view -> {
            if(listener != null) {
                this.listener.dateFieldPressed();
                if(dateDialog != null) {
                    whichDate = LoggingInputView.WhichDate.END;
                    this.dateDialog.show();
                }
            }
        });
    }

    /**
     * Function to check if dates are valid, Start date shouldn't pass the End date.
     */
    private void checkDateCorrectness(){
        if(sDate != null && eDate != null) {
            if(sDate.after(eDate) && listener != null) {
                this.listener.invalidDateSupplied();
                invalidDate = true;
            } else {
                invalidDate = false;
            }
        }
    }

    /**
     * Function to check if the setTime is valid
     */
    private void checkTimeCorrectness() {
        if(sDate != null && eDate != null) {
            if(sDate.after(eDate) && listener != null) {
                this.listener.invalidTimeSupplied();
                invalidTime = true;
            } else {
                invalidTime = false;
            }
        }
    }

    /**
     * Function to initialize  Calendar object by adding time to existing date
     * @param date
     * @param hourOfDay
     * @param minute
     * @return
     */
    private Calendar updateTimeOnCalendar(Date date, int hourOfDay, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }

    @Override
    public View getRootView() {
        return this.rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        this.actionBar = actionbar;
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setTitle(title);
    }

    @Override
    public void registerExportListener(LoggingInputListener listener) {
        this.listener = listener;
    }

    @Override
    public void setBreakSpinnerData(ArrayAdapter<String> adapter) {
        this.breakSpinner.setAdapter(adapter);
    }

    @Override
    public void setOverTimeSpinnerData(ArrayAdapter<String> adapter) {
        this.overTimeSpinner.setAdapter(adapter);
    }

    @Override
    public void setDateDialog(DatePickerDialog dialog) {
        if(dateDialog != null) {
            return;
        }

        this.dateDialog = dialog;
        dialog.setOnDateSetListener(this);
    }

    @Override
    public void setDateOnFields(String date) {
        this.startingDate.setText(date);
        this.endingDate.setText(date);
    }

    @Override
    public void setTimeDialog(TimePickerDialog dialog) {
        if(this.timeDialog != null) {
            return;
        }

        this.timeDialog = dialog;
    }

    @Override
    public void setTimeOnFields(String nowTime) {
        this.endingTime.setText(nowTime);
    }

    @Override
    public void enableOverTimeBox() {
        if (this.overTimeCheckBox.isChecked()) {
            this.overTimeLayout.setVisibility(overTimeLayout.VISIBLE);
        } else {
            this.overTimeLayout.setVisibility(overTimeLayout.INVISIBLE);
        }
    }

    @Override
    public boolean checkDateInvalid() {
        return invalidDate;
    }

    @Override
    public boolean checkTimeInvalid() {
        return invalidTime;
    }

    @Override
    public Date getStartDate() {
        return sDate;
    }

    @Override
    public Date getEndDate() {
        return eDate;
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }

    @Override
    public float getHoliday() {
        return Float.parseFloat(holidayHours.getText().toString());
    }

    @Override
    public float getWeekend() {
        return Float.parseFloat(weekendHours.getText().toString());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String date = String.format(Locale.getDefault(), "%s.%s.%s",
                String.valueOf(dayOfMonth),
                String.valueOf(month +1),               // +1 because the month is 0-based Array
                String.valueOf(year));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        switch(whichDate) {
             case START:
                startingDate.setText(date);
                sDate = cal.getTime();
                break;
            case END:
                endingDate.setText(date);
                eDate = cal.getTime();
                break;
            case NULL: break;
        }

        whichDate = LoggingInputView.WhichDate.NULL;
        checkDateCorrectness();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final String time = String.format(Locale.getDefault(), "%s:%s",
                String.valueOf(hourOfDay), String.valueOf(minute));

        switch (whichTime) {
            case START:
                startingTime.setText(time);
                sDate = updateTimeOnCalendar(sDate, hourOfDay, minute).getTime();
                break;

            case END:
                endingTime.setText(time);
                eDate = updateTimeOnCalendar(eDate, hourOfDay, minute).getTime();
                break;
            case NULL: break;
        }

        whichTime = LoggingInputView.WhichTime.NULL;
        checkTimeCorrectness();
    }
}
