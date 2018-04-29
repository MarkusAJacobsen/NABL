package com.ntnu.wip.nabl.MVCView.ExportView;

import android.app.DatePickerDialog;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

import java.util.Date;

/**
 * Created by a7med on 25.04.18.
 */
public interface IExportView extends IAbstractMvcView {
    interface ExportInputListener {
        void dateFieldPressed();
        void invalidDateSupplied();
        void exportBtnPressed();
        void changeSelectionBtnPressed();
    }

    void registerExportListner(ExportInputListener listener);
    void setDateDialog(DatePickerDialog dialog);
    void switchView();
    Date getStart();
    Date getEnd();
    String getProjectID();
    String getClientID();
}
