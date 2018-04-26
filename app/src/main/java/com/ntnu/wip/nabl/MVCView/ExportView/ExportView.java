package com.ntnu.wip.nabl.MVCView.ExportView;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
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

    private Date sDate;
    private Date eDate;
    private DatePickerDialog dateDialog;
    private ExportView.WhichDate whichDate = ExportView.WhichDate.NULL;

    private static final int PROJECT_ICON = R.drawable.project_button;
    private static final int CLIENT_ICON = R.drawable.client_button;
    private static final String PROJECT_TAG = "Project";
    private static final String CLIENT_TAG = "Client";

    @BindView(R.id.exportBtn) Button exportBtn;
    @BindView(R.id.projectsList) Spinner projectsList;
    @BindView(R.id.editTextStartDate) TextView startDate;
    @BindView(R.id.editTextEndDate) TextView endDate;
    @BindView(R.id.selectorBtn) ImageButton selectorBtn;
    @BindView(R.id.textViewChoose) TextView spinnerTitle;

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

    private void fetchClients() {
        //TODO Fetch Clients data
    }

    private void configureButton() {
        this.exportBtn.setOnClickListener(View -> {
            if(this.listener != null) {
                this.listener.exportBtnPressed();
            }
        });

        this.selectorBtn.setOnClickListener(View -> {
            if(this.listener != null) {
                this.listener.changeSelectionBtnPressed();
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
        // Implemented in ExportController
    }

    @Override
    public void setActionBarTitle(String title) {
        // Implemented in ExportController
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
    public void switchView() {
        if (this.selectorBtn.getTag() == this.PROJECT_TAG) {
            this.selectorBtn.setImageResource(this.CLIENT_ICON);
            this.selectorBtn.setTag(this.CLIENT_TAG);
            this.spinnerTitle.setText(R.string.clientList);

            // TODO fetch Clients to spinner
        } else {
            this.selectorBtn.setImageResource(this.PROJECT_ICON);
            this.selectorBtn.setTag(this.PROJECT_TAG);
            this.spinnerTitle.setText(R.string.projectList);

            // TODO fetch Projects to spinner
        }
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
    public String getProjectID() {
        //TODO get selected project's ID
        return null;
    }

    @Override
    public String getClientID() {
        //TODO get selected Client's ID
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
