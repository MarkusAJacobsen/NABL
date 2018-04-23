package com.ntnu.wip.nabl.MVCView.projectInput;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

import java.util.Date;

public interface IProjectInputView extends IAbstractMvcView{
    interface ProjectInputListener {
        void dateFieldPressed();
        void invalidDateSupplied();
        void getEditWarningDrawable();
    }

    void registerListener(ProjectInputListener listener);
    String getProjectId();
    String getProjectName();
    String getStreet();
    String getStreetNumber();
    String getZipcode();
    String getCity();
    Date getStart();
    Date getEnd();
    String getStatus();
    String getDescription();
    String getOrganisation();
    void setProjectId(String projectId);
    void setProjectName(String projectName);
    void setStreet(String street);
    void setStreetNumber(String streetNumber);
    void setZipcode(String zipcode);
    void setCity(String city);
    void setStart(String start);
    void setEnd(String end);
    void setStatus(String status);
    void setDescription(String description);
    void setOrganisation(String organisation);
    void setDateDialog(DatePickerDialog dialog);
    void populateStatusSpinner(Adapter options);
    void populateOrganisationSpinner(String[] options);
    boolean checkValidity();
    void setWarningColor(int color);
    void setWarningIcon(Drawable editWarningIcon);
}
