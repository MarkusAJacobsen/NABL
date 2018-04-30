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
        void dateFieldPressed();
        void invalidDateSupplied();
        void exportBtnPressed();
        void changeSelectionBtnPressed();
        void updateChosenObject(int pos, boolean mode);
    }

    // Functions to implement in the View
    void registerExportListener(ExportInputListener listener);
    void setDateDialog(DatePickerDialog dialog);
    boolean switchView();
    Date getStart();
    Date getEnd();
    void setResourceViewerAdapter(Adapter adapter);
}
