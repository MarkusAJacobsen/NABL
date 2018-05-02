package com.ntnu.wip.nabl.MVCView.ExportView;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ActionBar actionBar;
    private boolean switchMode = true;                 // True if Projects mode, False Clients mode

    private Date sDate;
    private Date eDate;
    private DatePickerDialog dateDialog;
    private ExportView.WhichDate whichDate = ExportView.WhichDate.NULL;

    private static final int PROJECT_ICON = R.drawable.project_button;
    private static final int CLIENT_ICON = R.drawable.client_button;
    private static final String PROJECT_TAG = "Project";
    private static final String CLIENT_TAG = "Client";

    @BindView(R.id.exportBtn) Button exportBtn;
    @BindView(R.id.elementList) Spinner elementList;
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

        configureDatePickers();
        configureButton();
        configureSpinner();
    }

    //---------------------------------------------------------------------------------------------
    // Private functions
    //

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
     * Function to configure selecting items on spinners
     */
    private void configureSpinner() {
        this.elementList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (listener != null){
                    listener.updateChosenObject(pos, switchMode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // NO OP
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
        this.actionBar = actionbar;
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setTitle(title);
    }

    @Override
    public void registerExportListener(ExportInputListener listener) {
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
    public boolean switchView() {
        if (this.selectorBtn.getTag() == this.PROJECT_TAG) {
            this.selectorBtn.setImageResource(this.CLIENT_ICON);
            this.selectorBtn.setTag(this.CLIENT_TAG);
            this.spinnerTitle.setText(R.string.clientList);

            this.switchMode = false;                    // Change to switch mode to client
        } else {
            this.selectorBtn.setImageResource(this.PROJECT_ICON);
            this.selectorBtn.setTag(this.PROJECT_TAG);
            this.spinnerTitle.setText(R.string.projectList);

            this.switchMode = true;                     // Change to switch mode to project
        }
        return this.switchMode;
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
    public void setResourceViewerAdapter(Adapter adapter) {
        this.elementList.setAdapter((ArrayAdapter) adapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Since Calendar uses constants to define months and January start at 0
        final int monthAddition = month +1;
        final String processedMonth = month < 10 ? "0"+  monthAddition : String.valueOf(monthAddition);
        final String processedDay = dayOfMonth < 10 ? "0"+dayOfMonth : String.valueOf(dayOfMonth);

        final String date = String.format(Locale.getDefault(), "%s.%s.%s",
                String.valueOf(processedDay),
                String.valueOf(processedMonth),
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
