package com.ntnu.wip.nabl.MVCControllers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.MVCView.ExportView.ExportView;
import com.ntnu.wip.nabl.MVCView.ExportView.IExportView;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observers.ClientCollectionObserver;
import com.ntnu.wip.nabl.Observers.Observers.ProjectCollectionObserver;
import com.ntnu.wip.nabl.R;

import java.io.File;
import java.util.ArrayList;
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

        new ProjectCollectionObserver(client).setOnUpdateListener(projects -> {
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

        new ClientCollectionObserver(client).setOnUpdateListener(clients -> {
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

        // Proof of concept of sending the file to Email or cloud storing
        mockMailSender();


        Toast.makeText(getApplicationContext(), "Pressed export button",
                Toast.LENGTH_SHORT).show();
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
     *
     */
    @Deprecated
    private void mockMailSender() {
        // TODO => ChosenObject hold the object that need to be exported either Project or client
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
    }
}
