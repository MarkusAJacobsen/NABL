package com.ntnu.wip.nabl.MVCView.ExportView;

import android.app.DatePickerDialog;
import android.widget.Adapter;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

import java.util.Date;

/**
 * Interface to connect between the ExportView (View) and it's Controller (ExportController)
 * Created by a7med on 25.04.18.
 */
public interface IExportView extends IAbstractMvcView {
    // Functions to implement in the Controller
    interface ExportInputListener {
        /**
         * Data field button pressed
         */
        void dateFieldPressed();

        /**
         * User has supplied invalid date
         */
        void invalidDateSupplied();

        /**
         * Export button pressed
         */
        void exportBtnPressed();

        /**
         * Change selection button has been pressed
         */
        void changeSelectionBtnPressed();

        /**
         * Update currently chosen model
         * @param pos int
         * @param mode boolean
         */
        void updateChosenObject(int pos, boolean mode);
    }

    /**
     * Register listener
     * @param listener ExportInputListener
     */
    void registerExportListener(ExportInputListener listener);

    /**
     * Set date dialog
     * @param dialog DatePickerDialog
     */
    void setDateDialog(DatePickerDialog dialog);

    /**
     * Switch which input which is shown
     * @return boolean
     */
    boolean switchView();

    /**
     * Get user inputted start date
     * @return Date
     */
    Date getStart();

    /**
     * Get user inputted end date
     * @return Date
     */
    Date getEnd();

    /**
     * Set resource selector list adapter
     * @param adapter Adapter
     */
    void setResourceViewerAdapter(Adapter adapter);

}
