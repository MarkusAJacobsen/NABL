package com.ntnu.wip.nabl.MVCView.LoggingView.LoggingInput;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @BindView(R.id.startingDate) TextView startingDate;
    @BindView(R.id.endingDate) TextView endingDate;
    @BindView(R.id.startingTime) TextView startingTime;
    @BindView(R.id.endingTime) TextView endingTime;
    @BindView(R.id.holidayStartTime) TextView holidayStartTime;
    @BindView(R.id.holidayEndTime) TextView holidayEndTime;
    @BindView(R.id.weekendStartTime) TextView weekendStartTime;
    @BindView(R.id.weekendEndTime) TextView weekendEndTime;
    @BindView(R.id.breaksCounter) Spinner breakSpinner;
    @BindView(R.id.overTimeCounter) Spinner overTimeSpinner;
    @BindView(R.id.checkBox) CheckBox overTimeCheckBox;
    @BindView(R.id.extraHours) ConstraintLayout overTimeLayout;

    private DatePickerDialog dateDialog;
    private TimePickerDialog timeDialog;

    private Date sDate;
    private Date eDate;

    private LoggingInputView.WhichDate whichDate = LoggingInputView.WhichDate.NULL;
    private LoggingInputView.WhichTime whichTime = LoggingInputView.WhichTime.NULL;

    private enum WhichDate {
        START,
        END,
        NULL
    }

    private enum WhichTime {
        START, END, HOLIDAY_START, HOLIDAY_END, WEEKEND_START, WEEKEND_END, NULL
    }

    /**
     * Constructor to create and initialize the view
     * @param inflater
     * @param container
     */
    public LoggingInputView(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.new_logging_input, container);
        ButterKnife.bind(this, rootView);

        configureDatePickers();
        configureTimePickers();
        configureCheckBox();
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
     * Function to configure the Time dialog
     */
    private void configureTimePickers() {
        configureStartAndEndTime();
        configureHolidayTime();
        configureWeekendTime();
    }

    /**
     * Function to configure the time dialog on clicking weekend fields
     */
    private void configureWeekendTime() {
        this.weekendStartTime.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.timeFieldPressed();
                if (timeDialog != null) {
                    this.whichTime = WhichTime.WEEKEND_START;
                    this.timeDialog.show();
                }
            }
        });

        this.weekendEndTime.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.timeFieldPressed();
                if (timeDialog != null) {
                    this.whichTime = WhichTime.WEEKEND_END;
                    this.timeDialog.show();
                }
            }
        });
    }

    /**
     * Function to configure the time dialog on clicking holiday fields
     */
    private void configureHolidayTime() {
        this.holidayStartTime.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.timeFieldPressed();
                if (timeDialog != null) {
                    this.whichTime = WhichTime.HOLIDAY_START;
                    this.timeDialog.show();
                }
            }
        });

        this.holidayEndTime.setOnClickListener(view -> {
            if (listener != null) {
                this.listener.timeFieldPressed();
                if (timeDialog != null) {
                    this.whichTime = WhichTime.HOLIDAY_END;
                    this.timeDialog.show();
                }
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
            }
        }
    }

    @Override
    public View getRootView() {
        return this.rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {

    }

    @Override
    public void setActionBarTitle(String title) {

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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final String date = String.format(Locale.getDefault(), "%s.%s.%s",
                String.valueOf(dayOfMonth),
                String.valueOf(month),
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
            case START: startingTime.setText(time); break;

            case END: endingTime.setText(time); break;

            case HOLIDAY_START: holidayStartTime.setText(time); break;

            case HOLIDAY_END: holidayEndTime.setText(time); break;

            case WEEKEND_START: weekendStartTime.setText(time); break;

            case WEEKEND_END: weekendEndTime.setText(time); break;

            case NULL: break;
        }
    }
}
