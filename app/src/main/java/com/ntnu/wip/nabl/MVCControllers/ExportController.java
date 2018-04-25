package com.ntnu.wip.nabl.MVCControllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ntnu.wip.nabl.MVCView.ExportView.ExportView;
import com.ntnu.wip.nabl.MVCView.ExportView.IExportView;
import com.ntnu.wip.nabl.R;

/**
 * Created by a7med on 25.04.18.
 */

public class ExportController extends AppCompatActivity implements IExportView.ExportInputListener {

    private ExportView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mvcView = new ExportView(getLayoutInflater(), null);
        this.mvcView.registerExportListner(this);
        this.mvcView.setActionBar(getSupportActionBar());
        this.mvcView.setActionBarTitle(getString(R.string.exportProjectsTitle));

        setContentView(this.mvcView.getRootView());
    }

    @Override
    public void dateFieldPressed() {
        DatePickerDialog dateDialog = new DatePickerDialog(ExportController.this);
        this.mvcView.setDateDialog(dateDialog);
    }

    @Override
    public void invalidDateSupplied() {

    }

    @Override
    public void getEditWarningDrawable() {

    }
}
