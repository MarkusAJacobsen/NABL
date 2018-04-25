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
    }

    void registerExportListner(ExportInputListener listener);
    void setDateDialog(DatePickerDialog dialog);
    Date getStart();
    Date getEnd();
    String getProjectName();
}
