package com.ntnu.wip.nabl.Network.FirestoreImpl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.LogEntry;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.FirestoreImpl.Callback.DocumentSnapshotCallback;
import com.ntnu.wip.nabl.Network.FirestoreImpl.Callback.QuerySnapshotCallback;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.CLIENT_COLLECTION;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.COMPANIES_COLLECTION;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.COMPANY_PROJECT_FIELD;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.COMPANY_USER_ID;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_CLIENT;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_COLLECTION;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_PROJECT;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_START_FIELD;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_STOP_FIELD;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.LOG_ENTRY_USER_ID;
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.PROJECT_COLLECTION;

public class FireStoreClient extends AbstractClient implements OnFailureListener {
    private FirebaseFirestore db;
    private Context context;

    public FireStoreClient(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    @Override
    public void getProject(String id) {
        get(PROJECT_COLLECTION, id, snapshot -> {
            Project project = snapshot.toObject(Project.class);
            setLastFetchedProject(project);
        });
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
    public void getClient(String id) {
        get(CLIENT_COLLECTION, id, snapshot -> {
            Client client = snapshot.toObject(Client.class);
            setLastFetchedClient(client);
        });
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
    public void getLogEntry(String id) {

    }

    @Override
    public void newLogEntry(WorkDay workDay) {
        this.add(LOG_ENTRY_COLLECTION, workDay, workDay.getId());
    }

    @Override
    public void getLogEntriesByUser(User user) {
        this.db.collection(LOG_ENTRY_COLLECTION).whereEqualTo(LOG_ENTRY_USER_ID, user.getId())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    List<WorkDay> workDays = new ArrayList<>();

                    for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                        WorkDay day = doc.toObject(WorkDay.class);
                        workDays.add(day);
                    }

                    this.setLastFetchedWorkdays(workDays);

                });
    }

    /**
     * Fetch log entries for a user that has specified either a Company or Project or neither
     * @param uid user identifier
     * @param cid client identifier (can be null)
     * @param pid project identifier (can be null)
     * @param startMillis from a time in milliseconds
     * @param stopMillis to time in milliseconds
     */
    @Override
    public void getLogEntries(String uid, String cid, String pid, long startMillis, long stopMillis) {
        CollectionReference collectionReference = this.db.collection(LOG_ENTRY_COLLECTION);
        Query query = collectionReference.whereEqualTo(LOG_ENTRY_USER_ID, uid);
        query.whereGreaterThan(LOG_ENTRY_START_FIELD, startMillis);
        query.whereLessThan(LOG_ENTRY_STOP_FIELD, stopMillis);

        if (cid.length() > 0) {
            query.whereEqualTo(LOG_ENTRY_CLIENT, cid);
        } else if (pid.length() > 0) {
            query.whereEqualTo(LOG_ENTRY_PROJECT, pid);
        }

        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<WorkDay> workDays = new ArrayList<>();

            for (QueryDocumentSnapshot snap: queryDocumentSnapshots) {
                WorkDay workDay = snap.toObject(WorkDay.class);
                workDays.add(workDay);
            }

            this.setLastFetchedWorkdays(workDays);
        });

        query.get();

    }

    @Override
    public void updateLogEntry(LogEntry entry) {

    }

    @Override
    public void deleteLogEntry(LogEntry entry) {

    }

    @Override
    public void newCompany(Company company) {
        this.add(COMPANIES_COLLECTION, company, company.getId());
    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(Company company) {
        this.delete(COMPANIES_COLLECTION, company.getId());
    }

    /**
     * Fetches companies based on a user
     * @param uid the owner of the companies
     */
    @Override
    public void getUserCompanies(String uid) {
        this.db.collection(COMPANIES_COLLECTION).whereEqualTo(COMPANY_USER_ID, uid)
        .addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<Company> companies = new ArrayList<>();

            for (QueryDocumentSnapshot snap: queryDocumentSnapshots) {
                Company company = snap.toObject(Company.class);
                companies.add(company);
            }

            this.setLastFetchedCompanies(companies);
        });

    }

    @Override
    public void getCompanyProjects(Company company) {
        this.db.collection(PROJECT_COLLECTION).whereEqualTo(COMPANY_PROJECT_FIELD, company.getId())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    List<Project> projects = new ArrayList<>();

                    for (QueryDocumentSnapshot snap: queryDocumentSnapshots) {
                        Project project = snap.toObject(Project.class);
                        projects.add(project);
                    }

                    this.setLastFetchedProjects(projects);
                });
    }

    @Override
    public void getCompanyClients(Company company) {
        this.db.collection(CLIENT_COLLECTION).whereEqualTo(COMPANY_PROJECT_FIELD, company.getId())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    List<Client> clients = new ArrayList<>();

                    for (QueryDocumentSnapshot snap: queryDocumentSnapshots) {
                        Client client = snap.toObject(Client.class);
                        clients.add(client);
                    }

                    this.setLastFetchedClients(clients);
                });

    }

    @Override
    public void getAllProjects() {
        fetchCollection(PROJECT_COLLECTION, snapshot -> {
            List<Project> toBeReturned = new ArrayList<>();

            for(QueryDocumentSnapshot doc : snapshot) {
                Project project = doc.toObject(Project.class);
                toBeReturned.add(project);
            }

            this.setProjects(toBeReturned);
        });
    }

    @Override
    public void getAllClients() {
        fetchCollection(CLIENT_COLLECTION, snapshot -> {
            List<Client> toBeReturned = new ArrayList<>();

            for(QueryDocumentSnapshot doc : snapshot) {
                Client client = doc.toObject(Client.class);
                toBeReturned.add(client);
            }

            this.setClients(toBeReturned);
        });
    }

    @Override
    public void getAllCompanies() {

    }

    @Override
    public void getAllLogEntries() {
    }

    @Override
    public void getProjectSpecificLogEntries(Project project) {
    }

    @Override
    public void getClientSpecificLogEntries(Client client) {

    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.w("FireStoreClient", e);
    }

    private void get(String collection, String id, DocumentSnapshotCallback callback) {
        db.collection(collection)
                .document(id)
                .get()
                .addOnFailureListener(this)
                .addOnSuccessListener(callback::trigger);
    }

    private void add(String collection, Object toWrite, String id){
        db.collection(collection)
                .document(id).
                set(toWrite).
                addOnFailureListener(this);
    }

    private void update(String collection, Object toUpdate, String documentId) {
        db.collection(collection)
                .document(documentId)
                .set(toUpdate)
                .addOnFailureListener(this)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, R.string.updateComplete, Toast.LENGTH_SHORT).show());
    }

    private void delete(String collection, String documentId) {
        db.collection(collection)
                .document(documentId)
                .delete()
                .addOnFailureListener(this)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, R.string.successDeleted, Toast.LENGTH_SHORT).show());
    }

    private void fetchCollection(String collection, QuerySnapshotCallback callback) {
        db.collection(collection)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.trigger(task.getResult());
                    } else {
                       Toast.makeText(context, R.string.unableTofetchResource, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
