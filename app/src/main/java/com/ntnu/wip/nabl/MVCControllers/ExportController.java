package com.ntnu.wip.nabl.MVCControllers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ntnu.wip.nabl.MVCView.ExportView.ExportView;
import com.ntnu.wip.nabl.MVCView.ExportView.IExportView;
import com.ntnu.wip.nabl.R;

import java.io.File;

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
        Toast.makeText(getApplicationContext(), getString(R.string.invalidDate),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exportBtnPressed() {
        //TODO => Generate the File
        //TODO => String with File Name
        //TODO => File (File Location)
        //TODO => Start new intent and pass everything for it
        //TODO => Follow this for the rest => https://stackoverflow.com/questions/9974987/how-to-send-an-email-with-a-file-attachment-in-android

        @Deprecated
        String filename = "assignment1.pdf";
        File fileLocation = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),
                filename);
        Uri path = Uri.fromFile(fileLocation);


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"asmadhun@stud.ntnu.no"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        // the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Your Log file");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));



        Toast.makeText(getApplicationContext(), "Email sended !!",
                Toast.LENGTH_SHORT).show();
    }
}
