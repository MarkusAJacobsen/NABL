package com.ntnu.wip.nabl.MVCView.ExportView;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a7med on 25.04.18.
 */
public class ExportView implements IExportView, DatePickerDialog.OnDateSetListener {
    private View rootView;
    private ExportInputListener listener;

    @BindView(R.id.exportBtn) Button exportBtn;
    @BindView(R.id.projectsList) Spinner projectsList;
    @BindView(R.id.editTextStartDate) TextView startDate;
    @BindView(R.id.editTextEndDate) TextView endDate;

    private Date sDate;
    private Date eDate;
    private DatePickerDialog dateDialog;
    private ExportView.WhichDate whichDate = ExportView.WhichDate.NULL;

    private enum WhichDate {
        START,
        END,
        NULL
    }

    public ExportView(LayoutInflater inflater, ViewGroup container) {
        this.rootView = inflater.inflate(R.layout.export_layout, container);

        ButterKnife.bind(this, rootView);

        fetchProjects();
        configureDatePickers();
        configureButton();
    }

    //---------------------------------------------------------------------------------------------
    // Private functions
    //

    /**
     * Function to fetch project from firebase
     */
    private void fetchProjects() {
        //TODO Fetch projects
    }

    private void configureButton() {
        this.exportBtn.setOnClickListener(View -> {
            if(listener != null) {
                this.listener.exportBtnPressed();
            }
        });
    }

    /**
     * Function to show the calendar dialog when clicking on Start and End Dates fields
     */
    private void configureDatePickers(){
        this.startDate.setOnClickListener(view -> {
            if(listener != null) {
                this.listener.dateFieldPressed();
                if(dateDialog != null) {
                    whichDate = ExportView.WhichDate.START;
                    this.dateDialog.show();
                }
            }
        });

        this.endDate.setOnClickListener(view -> {
            if(listener != null) {
                this.listener.dateFieldPressed();
                if(dateDialog != null) {
                    whichDate = ExportView.WhichDate.END;
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

    //
    // End of privates functions
    //---------------------------------------------------------------------------------------------
    // Interface Implementation
    //

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        // NO OP
    }

    @Override
    public void setActionBarTitle(String title) {
        // NO OP
    }

    @Override
    public void registerExportListner(ExportInputListener listener) {
        this.listener = listener;
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
    public Date getStart() {
        return sDate;
    }

    @Override
    public Date getEnd() {
        return eDate;
    }

    @Override
    public String getProjectName() {
        //TODO get project name and send it further
        return null;
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
                startDate.setText(date);
                sDate = cal.getTime();
                break;
            case END:
                endDate.setText(date);
                eDate = cal.getTime();
                break;
            case NULL: break;
        }

        whichDate = ExportView.WhichDate.NULL;
        checkDateCorrectness();
    }
}
