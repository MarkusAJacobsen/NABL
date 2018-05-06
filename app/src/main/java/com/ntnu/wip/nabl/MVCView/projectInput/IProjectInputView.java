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

    /**
     * Get text from field 'ProjectId'
     * @return String
     */
    String getProjectId();

    /**
     * Get text from field 'Project name'
     * @return String
     */
    String getProjectName();

    /**
     * Get text from field 'Street'
     * @return String
     */
    String getStreet();

    /**
     * Get text from field 'Street number'
     * @return String
     */
    String getStreetNumber();

    /**
     * Get text from field 'Zipcode'
     * @return String
     */
    String getZipcode();

    /**
     * Get text from field 'City'
     * @return String
     */
    String getCity();

    /**
     * Get date from field 'Start'
     * @return Date
     */
    Date getStart();

    /**
     * Get date from field 'End'
     * @return Date
     */
    Date getEnd();

    /**
     * Get text from field 'Status'
     * @return String
     */
    String getStatus();

    /**
     * Get text from field 'Description'
     * @return String
     */
    String getDescription();

    /**
     * Get text from field 'Organisation'
     * @return String
     */
    String getOrganisation();

    /**
     * Set text to field 'ProjectId'
     * @param projectId String
     */
    void setProjectId(String projectId);

    /**
     * Set text to field 'Project name '
     * @param projectName String
     */
    void setProjectName(String projectName);

    /**
     * Set text to field 'Street'
     * @param street String
     */
    void setStreet(String street);

    /**
     * Set text to field 'Street number'
     * @param streetNumber String
     */
    void setStreetNumber(String streetNumber);

    /**
     * Set text to field 'Zipcode'
     * @param zipcode String
     */
    void setZipcode(String zipcode);

    /**
     * Set text to field 'City'
     * @param city String
     */
    void setCity(String city);

    /**
     * Set text to field 'Start'
     * @param start String
     */
    void setStart(String start);

    /**
     * Set text to field 'End'
     * @param end String
     */
    void setEnd(String end);

    /**
     * Set text to field 'Status'
     * @param status String
     */
    void setStatus(String status);

    /**
     * Set text to field 'Description'
     * @param description String
     */
    void setDescription(String description);

    /**
     * Set text to field 'Organisation'
     * @param organisation String
     */
    void setOrganisation(String organisation);

    /**
     * Set date dialog
     * @param dialog DatePickerDialog
     */
    void setDateDialog(DatePickerDialog dialog);

    /**
     * Set field 'Status' Spinner adapter
     * @param options Adapter
     */
    void populateStatusSpinner(Adapter options);

    /**
     * Set field 'Organisation' Spinner adapter
     * @param options Adapter
     */
    void populateOrganisationSpinner(String[] options);

    /**
     * Validate user input fields validity
     * @return boolean - true if valid, false if not
     */
    boolean checkValidity();

    /**
     * Set warning icon color
     * @param color int
     */
    void setWarningColor(int color);

    /**
     * Set warning icon
     * @param editWarningIcon Drawable
     */
    void setWarningIcon(Drawable editWarningIcon);
}
