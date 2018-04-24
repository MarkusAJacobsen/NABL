package com.ntnu.wip.nabl.Network.FirestoreImpl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.LogEntry;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.IClient;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.CLIENT_COLLECTION;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_COLLECTION;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.PROJECT_COLLECTION;

public class FireStoreClient implements IClient, OnFailureListener {
    private FirebaseFirestore db;
    private Context context;

    public FireStoreClient(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    @Override
    public void registerListener() {

    }

    @Override
    public void writeNewProject(Project project) {
        this.add(PROJECT_COLLECTION, project, project.getId());
    }

    @Override
    public void updateProject(Project project) {
        this.update(PROJECT_COLLECTION, project, project.getId());
    }

    @Override
    public void deleteProject(Project project) {
        this.delete(PROJECT_COLLECTION, project.getId());
    }

    @Override
    public void writeNewClient(Client client) {
        this.add(CLIENT_COLLECTION, client, client.getId());
    }

    @Override
    public void updateClient(Client client) {
        this.update(CLIENT_COLLECTION, client, client.getId());
    }

    @Override
    public void deleteClient(Client client) {
        this.delete(CLIENT_COLLECTION, client.getId());
    }

    @Override
    public void newLogEntry(LogEntry entry) {
        add(LOG_ENTRY_COLLECTION, entry, entry.getId());
    }

    @Override
    public void updateLogEntry(LogEntry entry) {

    }

    @Override
    public void deleteLogEntry(LogEntry entry) {

    }

    @Override
    public void newCompany(Company company) {

    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(Company company) {

    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> toBeReturned = new ArrayList<>();
        QuerySnapshot snapshot = fetchCollection(PROJECT_COLLECTION);

        for(QueryDocumentSnapshot doc : snapshot) {
            Project project = doc.toObject(Project.class);
            toBeReturned.add(project);
        }

        return toBeReturned;
    }

    @Override
    public List<Client> getAllClients() {
        return null;
    }

    @Override
    public List<Company> getAllCompanies() {
        return null;
    }

    @Override
    public List<LogEntry> getAllLogEntries() {
        return null;
    }

    @Override
    public List<LogEntry> getProjectSpecificLogEntries(Project project) {
        return null;
    }

    @Override
    public List<LogEntry> getClientSpecificLogEntries(Client client) {
        return null;
    }

    private void add(String collection, Object toWrite, String id){
        db.collection(collection).document(id).set(toWrite).addOnFailureListener(this);
    }

    private void update(String collection, Object toUpdate, String documentId) {
        db.collection(collection).document(documentId).set(toUpdate).addOnFailureListener(this)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, R.string.updateComplete, Toast.LENGTH_SHORT).show());
    }

    private void delete(String collection, String documentId) {
        db.collection(collection).document(documentId).delete().addOnFailureListener(this)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, R.string.successDeleted, Toast.LENGTH_SHORT).show());
    }

    private QuerySnapshot fetchCollection(String collection) {
        final QuerySnapshot[] toBeReturned = new QuerySnapshot[1];
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        toBeReturned[0] =  task.getResult();
                    } else {
                       Toast.makeText(context, R.string.unableTofetchResource, Toast.LENGTH_SHORT).show();
                    }
                });
        return toBeReturned[0];
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.w("FireStoreClient", e);
    }
}
