package com.ntnu.wip.nabl.MVCControllers;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.MVCView.ExportView.ExportView;
import com.ntnu.wip.nabl.MVCView.ExportView.IExportView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.ContactInformation;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.TimeSheet;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;
import com.ntnu.wip.nabl.R;

import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller class that represent the Exporting Activity
 * Created by a7med on 25.04.18.
 */
public class ExportController extends AppCompatActivity implements IExportView.ExportInputListener {
    private ExportView mvcView;
    private List<Project> projects = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private Object chosenObject = new Object();

    private static final String FILE_LOCATION = "exportFile.xlsx";
    private static final int PERMISSION_EXTERNAL_STORAGE = 2279;

    // This is set when the user is asked for permission
    private TimeSheet temporaryTimeSheet;

    /**
     * Android Activity life cycle function
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mvcView = new ExportView(getLayoutInflater(), null);
        this.mvcView.registerExportListener(this);

        this.mvcView.setActionBar(getSupportActionBar());
        this.mvcView.setActionBarTitle(getString(R.string.exportProjectsTitle));

        setContentView(this.mvcView.getRootView());

        fetchProjects();
    }

    /**
     * Function to fetch projects from FireBase
     */
    private void fetchProjects() {
        this.projects.clear();
        FireStoreClient client = new FireStoreClient(getApplicationContext());
        client.getAllProjects();

        Observer observer = ObserverFactory.create(ObserverFactory.PROJECT_COLLECTION);
        observer.setSubject(client);
        observer.setOnUpdateListener(projects -> {
            this.projects = (List) projects;
            if (!this.projects.isEmpty() || this.projects != null) {
                this.chosenObject = (Project) this.projects.get(0);     // First element
            }
            Adapter adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, this.projects);
            mvcView.setResourceViewerAdapter(adapter);
        });
    }

    /**
     * Function to fetch clients from FireBase
     */
    private void fetchClients() {
        this.clients.clear();
        FireStoreClient client = new FireStoreClient(getApplicationContext());
        client.getAllClients();

        Observer observer = ObserverFactory.create(ObserverFactory.CLIENT_COLLECTION);
        observer.setSubject(client);
        observer.setOnUpdateListener(clients -> {
            this.clients = (List) clients;
            if (!this.clients.isEmpty() || this.clients != null) {
                this.chosenObject = (Client) this.clients.get(0);     // First element
            }
            Adapter adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, this.clients);
            mvcView.setResourceViewerAdapter(adapter);
        });
    }

    /**
     * Function to show the Date Dialog when date field in ExportView is pressed
     */
    @Override
    public void dateFieldPressed() {
        DatePickerDialog dateDialog = new DatePickerDialog(ExportController.this);
        this.mvcView.setDateDialog(dateDialog);
    }

    /**
     * Function to give a message to the user for giving invalid date on ExportView
     */
    @Override
    public void invalidDateSupplied() {
        Toast.makeText(getApplicationContext(), getString(R.string.invalidDate),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Function to response to using clicking on export button on ExportView
     */
    @Override
    public void exportBtnPressed() {
        //TODO => Generate the File
        FireStoreClient client = new FireStoreClient(this);
        FirestoreAuthentication firestoreAuthentication = new FirestoreAuthentication();

        client.attach(new Observer() {
            @Override
            public void setSubject(IObserverSubject subject) {

            }

            @Override
            public void update() {

            }

            @Override
            public void update(Subscriptions sub) {
                TimeSheet sheet;
                User user = new User(firestoreAuthentication.getUId(), "missing", new ContactInformation());

                if (sub == Subscriptions.LOG_ENTRIES) {
                    if (chosenObject.getClass() == Project.class) {
                        sheet = new TimeSheet(getApplicationContext(), (Project) chosenObject, user, client.getLastFetchedWorkdays());
                    } else {
                        sheet = new TimeSheet(getApplicationContext(), (Client) chosenObject, user, client.getLastFetchedWorkdays());
                    }

                    timeSheetWriteExport(sheet);
                }
            }


            @Override
            public void setOnUpdateListener(AddOnUpdateListener listener) {

            }
        });

        // Proof of concept of sending the file to Email or cloud storing
        if (chosenObject.getClass() == Project.class) {
            Project project = (Project) chosenObject;
            client.getLogEntries(firestoreAuthentication.getUId(), "", project.getId(),
                    mvcView.getStart().getTime(), mvcView.getEnd().getTime());
        } else {
            Client clientC = (Client) chosenObject;
            client.getLogEntries(firestoreAuthentication.getUId(), clientC.getId(), "",
                    mvcView.getStart().getTime(), mvcView.getEnd().getTime());
        }

    }


    /**
     * This function handles asking for permissions and similar
     * @param timeSheet timesheet to be written
     */
    private void timeSheetWriteExport(TimeSheet timeSheet) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            temporaryTimeSheet = timeSheet;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_EXTERNAL_STORAGE);

        } else {
            try {
                String name = timeSheetName(timeSheet);
                timeSheet.write(name);
                mockMailSender(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    timeSheetWriteExport(temporaryTimeSheet);
                } else {
                    // Grant was not given by the user
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private String timeSheetName(TimeSheet sheet) {
        DateTime dt = new DateTime();

        String name = "timesheet_";
        name += sheet.extractPeriod();
        name += "_"+dt.toDate().getTime();
        name += ".xlsx";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * function to fetch either projects or clients depending on the ExportView
     */
    @Override
    public void changeSelectionBtnPressed() {
        final boolean mode = this.mvcView.switchView();
        if(mode) {
            fetchProjects();
        } else {
            fetchClients();
        }
    }

    /**
     * Function that assign the chosenObject on the View
     * @param pos       element position on it's list
     * @param viewMode  <True>if project</True> .. <False>if Client</False>
     */
    @Override
    public void updateChosenObject(int pos, boolean viewMode) {
        if (viewMode) {
            if (this.projects.size()>0) {
                this.chosenObject = (Project) this.projects.get(pos);
            }
        } else {
            if (this.clients.size()>0) {
                this.chosenObject = (Client) this.clients.get(pos);
            }
        }
    }

    /**
     * Proof of concept, follow this
     * https://stackoverflow.com/questions/9974987/how-to-send-an-email-with-a-file-attachment-in-android
     * @param filename absolute path of a file
     */
    private void mockMailSender(String filename) {
        Uri path = Uri.fromFile(new File(filename));


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"martkli@stud.ntnu.no"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, path);
        // the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Your Log file");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));
    }
}
