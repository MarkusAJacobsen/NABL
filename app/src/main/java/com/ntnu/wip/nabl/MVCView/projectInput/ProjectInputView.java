package com.ntnu.wip.nabl.MVCView.projectInput;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectInputView implements IProjectInputView, DatePickerDialog.OnDateSetListener {
    private View rootView;
    private ProjectInputListener listener;
    private int warningColor = Color.RED;
    private Drawable editWarning;

    @BindView(R.id.projectId) TextView mProjectId;
    @BindView(R.id.projectName) TextView mProjectName;
    @BindView(R.id.street) TextView mStreet;
    @BindView(R.id.streetNumber) TextView mStreetNumber;
    @BindView(R.id.zipcode) TextView mZipcode;
    @BindView(R.id.city) TextView mCity;
    @BindView(R.id.dateStart) TextView mDateStart;
    @BindView(R.id.dateEnd) TextView mDateEnd;
    @BindView(R.id.statusSpinner) Spinner mStatus;
    @BindView(R.id.descriptionTitle) TextView mDescription;
    @BindView(R.id.organisation) Spinner mOrganisation;

    private DatePickerDialog dateDialog;
    private WhichDate whichDate = WhichDate.NULL;
    private Date startDate;
    private Date endDate;

    private enum WhichDate {
        START,
        END,
        NULL
    }

    private List<TextView> inputRequiredFields;


    public ProjectInputView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.project_input, container);

        ButterKnife.bind(this, rootView);

        configureTextViewList();
        configureDatePickers();

    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {

    }

    @Override
    public void setActionBarTitle(String title) {

    }

    @Override
    public void registerListener(ProjectInputListener listener) {
        this.listener = listener;
    }

    @Override
    public String getProjectId() {
        return mProjectId.getText().toString();
    }

    @Override
    public String getProjectName() {
        return mProjectName.getText().toString();
    }

    @Override
    public String getStreet() {
        return mStreet.getText().toString();
    }

    @Override
    public String getStreetNumber() {
        return mStreetNumber.getText().toString();
    }

    @Override
    public String getZipcode() {
        return mZipcode.getText().toString();
    }

    @Override
    public String getCity() {
        return mCity.getText().toString();
    }

    @Override
    public Date getStart() {
        return startDate;
    }

    @Override
    public Date getEnd() {
        return endDate;
    }

    @Override
    public String getStatus() {
        return mStatus.getSelectedItem().toString();
    }

    @Override
    public String getDescription() {
        return mDescription.getText().toString();
    }

    @Override
    public String getOrganisation() {
        return mOrganisation.getSelectedItem() != null ? mOrganisation.getSelectedItem().toString() : null;
    }

    @Override
    public void setProjectId(String projectId) {
        mProjectId.setText(projectId);
    }

    @Override
    public void setProjectName(String projectName) {
        mProjectName.setText(projectName);
    }

    @Override
    public void setStreet(String street) {
        mStreet.setText(street);
    }

    @Override
    public void setStreetNumber(String streetNumber) {
        mStreetNumber.setText(streetNumber);
    }

    @Override
    public void setZipcode(String zipcode) {
        mZipcode.setText(zipcode);
    }

    @Override
    public void setCity(String city) {
        mCity.setText(city);
    }

    @Override
    public void setStart(String start) {
        mDateStart.setText(start);
        establishDate(WhichDate.START, start);
    }

    @Override
    public void setEnd(String end) {
        mDateEnd.setText(end);
        establishDate(WhichDate.END, end);
    }

    @Override
    public void setStatus(String status) {
        SpinnerAdapter adapter = mStatus.getAdapter();
        adapter.getCount();
        List<String> spinnerItems = new ArrayList<>();

        for(int i = 0; i < adapter.getCount(); i++) {
            spinnerItems.add((String) adapter.getItem(i));
        }

        for(String item : spinnerItems) {
            if(status.equals(item)) {
                mStatus.setSelection(spinnerItems.indexOf(item));
            }
        }
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void setOrganisation(String status) {

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
    public void populateStatusSpinner(Adapter options) {
        mStatus.setAdapter((SpinnerAdapter) options);
    }

    @Override
    public void populateOrganisationSpinner(String[] options) {

    }

    @Override
    public boolean checkValidity() {
        boolean emptyFieldsPresent = true;

        for(TextView fieldToCheck : inputRequiredFields) {
            if(fieldToCheck.getText().toString().trim().equals("")) {
                setWarningDrawable(fieldToCheck);
                emptyFieldsPresent = false;
            } else {
                tryToRemoveDrawable(fieldToCheck);
            }
        }

        return emptyFieldsPresent;
    }

    @Override
    public void setWarningColor(int color) {
        warningColor = color;
    }

    @Override
    public void setWarningIcon(Drawable editWarningIcon) {
        this.editWarning = editWarningIcon;
    }


    private void configureDatePickers(){
        mDateStart.setOnClickListener(view -> {
            if(listener != null) {
                listener.dateFieldPressed();
                if(dateDialog != null) {
                    whichDate = WhichDate.START;
                    dateDialog.show();
                }
            }
        });

        mDateEnd.setOnClickListener(view -> {
            if(listener != null) {
                listener.dateFieldPressed();
                if(dateDialog != null) {
                    whichDate = WhichDate.END;
                    dateDialog.show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
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
                mDateStart.setText(date);
                startDate = cal.getTime();
                break;
            case END:
                mDateEnd.setText(date);
                endDate = cal.getTime();
                break;
            case NULL: break;
        }

        whichDate = WhichDate.NULL;
        checkDateCorrectness();
    }

    private void checkDateCorrectness(){
        if(startDate != null && endDate != null) {
            if(startDate.after(endDate) && listener != null) {
                listener.invalidDateSupplied();
            }
        }
    }

    private void setWarningDrawable(TextView view){
        getWarningEditIcon();

        if(editWarning == null) {
            return;
        }

        editWarning.setBounds(0, 0, editWarning.getIntrinsicWidth(), editWarning.getIntrinsicHeight());
        editWarning.setColorFilter(warningColor, PorterDuff.Mode.SRC_ATOP);
        view.setCompoundDrawables(null, null, editWarning, null);
    }

    private void getWarningEditIcon(){
        if(editWarning == null && listener != null) {
            listener.getEditWarningDrawable();
        }
    }

    private void configureTextViewList(){
        inputRequiredFields = new ArrayList<>();
        inputRequiredFields.add(mProjectId);
        inputRequiredFields.add(mProjectName);
        inputRequiredFields.add(mStreet);
        inputRequiredFields.add(mStreetNumber);
        inputRequiredFields.add(mCity);
        inputRequiredFields.add(mZipcode);
    }

    private void tryToRemoveDrawable(TextView view) {
        if(view.getCompoundDrawables() != null) {
            view.setCompoundDrawables(null, null, null, null);
        }
    }

    private void establishDate(WhichDate whichDate, String dateStringFormat) {
        Calendar cal = Calendar.getInstance();

        List<Integer> decodedDate = Utils.decodeDateString(dateStringFormat);

        cal.set(Calendar.DAY_OF_MONTH, decodedDate.get(0));
        cal.set(Calendar.MONTH, (decodedDate.get(1))); // January is considered JANUARY(0)
        cal.set(Calendar.YEAR, decodedDate.get(2));

        switch(whichDate){
            case START: startDate = cal.getTime(); break;
            case END: endDate = cal.getTime(); break;
            default: break;
        }
    }
}

