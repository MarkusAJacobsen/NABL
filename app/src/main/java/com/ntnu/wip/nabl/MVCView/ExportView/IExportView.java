package com.ntnu.wip.nabl.MVCView.ExportView;

import android.app.DatePickerDialog;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by a7med on 25.04.18.
 */
public interface IExportView extends IAbstractMvcView {
    interface ExportInputListener {
        void dateFieldPressed();
        void invalidDateSupplied();
        void getEditWarningDrawable();
    }

    void registerExportListner(ExportInputListener listener);
    void setDateDialog(DatePickerDialog dialog);
}
