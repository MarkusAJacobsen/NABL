package com.ntnu.wip.nabl.Network.FirestoreImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.Authentication.IAuthentication;
import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCControllers.Settings;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.FirestoreImpl.Callback.DocumentSnapshotCallback;
import com.ntnu.wip.nabl.Network.FirestoreImpl.Callback.QuerySnapshotCallback;
import com.ntnu.wip.nabl.R;

import java.lang.reflect.Type;
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
import static com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreStatics.ROOT_LEVEL_DATA;

/**
 * Firestore network implementation
 * For docs see {@link com.ntnu.wip.nabl.Network.IClient}
 */
public class FireStoreClient extends AbstractClient implements OnFailureListener {
    private FirebaseFirestore db;
    private Context context;
    private IAuthentication auth;
    private String loggedInUser;
    private Company company;

    public FireStoreClient(Context context) {
        db = FirebaseFirestore.getInstance();
        this.context = context;
        establishUserAndCompany();
    }

    /**
     * Get logged in user and user saved company workspace
     * This is needed to insert and fetch from the database
     * in a hierarchly correct way
     */
    private void establishUserAndCompany() {
        auth = new FirestoreAuthentication();
        loggedInUser = auth.getEmail();
        getCompanyFromPreferences();
    }

    /**
     * Get company from preferences, this is sat in settings
     * If no company is sat return null
     */
    private void getCompanyFromPreferences() {
        SharedPreferences preferences = context.getSharedPreferences(Settings.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        if(preferences.contains(Settings.SELECTED_WORKSPACE_PREFERENCE_FIELD)) {
            final String companyString = preferences.getString(Settings.SELECTED_WORKSPACE_PREFERENCE_FIELD, "");

            if(companyString.equals("")) {
                return;
            }

            final Type token = new TypeToken<Company>(){}.getType();
            company = new Gson().fromJson(companyString, token);
        }
    }

    /**
     * Method for checking the presence of Company else abort execution
     * @throws CompanyNotFoundException - Company not sat in settings
     */
    private void checkCompany() throws CompanyNotFoundException {
        if(company == null) {
            abort();
        }
    }

    /**
     * If execution abort is needed. E.g. company not sat
     * @throws CompanyNotFoundException Company not found
     * TODO generify
     */
    private void abort() throws CompanyNotFoundException{
       throw new CompanyNotFoundException();
    }

    @Override
    public void getProject(String id) throws CompanyNotFoundException {
        checkCompany();

        getNested(ROOT_LEVEL_DATA, company.getName(), PROJECT_COLLECTION, id, snapshot -> {
            Project project = snapshot.toObject(Project.class);
            setLastFetchedProject(project);
        });
    }

    @Override
    public void writeNewProject(Project project) throws CompanyNotFoundException {
        checkCompany();

        this.addNested(project, project.getId(), ROOT_LEVEL_DATA, company.getName(), PROJECT_COLLECTION);
    }

    @Override
    public void updateProject(Project project) throws CompanyNotFoundException {
        checkCompany();

        this.updateNested(ROOT_LEVEL_DATA, company.getName(), PROJECT_COLLECTION, project, project.getId());
    }

    @Override
    public void deleteProject(Project project) throws CompanyNotFoundException {
        checkCompany();

        this.deleteNested(ROOT_LEVEL_DATA, company.getName(), PROJECT_COLLECTION, project.getId());
    }

    @Override
    public void getClient(String id) throws CompanyNotFoundException {
        checkCompany();

        getNested(ROOT_LEVEL_DATA, company.getName(), CLIENT_COLLECTION, id, snapshot -> {
            Client client = snapshot.toObject(Client.class);
            setLastFetchedClient(client);
        });
    }

    @Override
    public void writeNewClient(Client client) throws CompanyNotFoundException {
        checkCompany();

        this.addNested(client, client.getId(), ROOT_LEVEL_DATA, company.getName(), CLIENT_COLLECTION);
    }

    @Override
    public void updateClient(Client client) throws CompanyNotFoundException {
        checkCompany();

        this.updateNested(ROOT_LEVEL_DATA, company.getName(), CLIENT_COLLECTION, client, client.getId());
    }

    @Override
    public void deleteClient(Client client) throws CompanyNotFoundException {
        checkCompany();

        this.deleteNested(ROOT_LEVEL_DATA, company.getName(), CLIENT_COLLECTION, client.getId());
    }

    @Override
    public void getLogEntry(String id) {

    }

    @Override
    public void newLogEntry(WorkDay workDay) throws CompanyNotFoundException {
        checkCompany();

        String resource;
        String resourceId;
        if(workDay.getClientId() != null) {
            resource = CLIENT_COLLECTION;
            resourceId = workDay.getClientId();
        } else {
            resource = PROJECT_COLLECTION;
            resourceId = workDay.getProjectId();
        }

        //Save log entry in Company -> Client/Project -> Corresponding project/client -> logEntries
        db.collection(ROOT_LEVEL_DATA)
                .document(company.getName())
                .collection(resource)
                .document(resourceId)
                .collection(LOG_ENTRY_COLLECTION)
                .document(workDay.getId())
                .set(workDay)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, R.string.hourLogSaved, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(this);

        //For convenience save entries in root -> logEntries as well, this makes it easier to fetch
        //but is a way of double storing. Optimally you will have to iterate all user companies
        //and all collections below and sort TODO
        add(LOG_ENTRY_COLLECTION, workDay, workDay.getId());
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
     * TODO Martin Klingenberg, handle DB hierarchy same as the rest. Not entirely sure whats happening here
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
    public void getAllProjects() throws CompanyNotFoundException {
        checkCompany();

        fetchCollectionNested(ROOT_LEVEL_DATA, company.getName(), PROJECT_COLLECTION, snapshot -> {
            List<Project> toBeReturned = new ArrayList<>();

            for(QueryDocumentSnapshot doc : snapshot) {
                Project project = doc.toObject(Project.class);
                toBeReturned.add(project);
            }

            this.setProjects(toBeReturned);
        });
    }

    @Override
    public void getAllClients() throws CompanyNotFoundException {
        if(company == null) {
            abort();
        }

        fetchCollectionNested(ROOT_LEVEL_DATA, company.getName(), CLIENT_COLLECTION, snapshot -> {
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
        fetchCollection(LOG_ENTRY_COLLECTION,  snapshot -> {
            List<Company> toBeReturned = new ArrayList<>();

            for(QueryDocumentSnapshot doc : snapshot) {
                Company company = doc.toObject(Company.class);
                toBeReturned.add(company);
            }

            this.setLastFetchedCompanies(toBeReturned);
        });
    }

    @Override
    public void getAllLogEntries() {
        fetchCollection(LOG_ENTRY_COLLECTION,  snapshot -> {
            List<WorkDay> toBeReturned = new ArrayList<>();

            for(QueryDocumentSnapshot doc : snapshot) {
                WorkDay workDay = doc.toObject(WorkDay.class);
                toBeReturned.add(workDay);
            }

            this.setLastFetchedWorkdays(toBeReturned);
        });
    }

    @Override
    public void getProjectSpecificLogEntries(Project project) throws CompanyNotFoundException {
        checkCompany();

        db.collection(ROOT_LEVEL_DATA)
                .document(company.getName())
                .collection(PROJECT_COLLECTION)
                .document(project.getId())
                .collection(LOG_ENTRY_COLLECTION)
                .get()
                .addOnFailureListener(this)
                .addOnSuccessListener(snapshots -> {
                    //TODO
                });
    }

    @Override
    public void getClientSpecificLogEntries(Client client) throws CompanyNotFoundException {
        checkCompany();

        db.collection(ROOT_LEVEL_DATA)
                .document(company.getName())
                .collection(CLIENT_COLLECTION)
                .document(client.getId())
                .collection(LOG_ENTRY_COLLECTION)
                .get()
                .addOnFailureListener(this)
                .addOnSuccessListener(snapshots -> {
                    //TODO
                });
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

    private void getNested(String root, String childName, String resourceType, String id,
                           DocumentSnapshotCallback callback) {
        db.collection(root)
                .document(childName)
                .collection(resourceType)
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

    /**
     * A bit on the dumber side, but since you cant actually do something with a collection,
     * we are limited to a even number of identifiers, so this method is customized for our need
     * and not very dynamic
     * @param toWrite Object
     * @param id String
     * @param root String - top level identifier
     * @param childName String - Middle level identifier
     * @param resourceType String - Last level identifier
     */
    private void addNested(Object toWrite, String id, String root, String childName, String resourceType){
        db.collection(root)
                .document(childName)
                .collection(resourceType)
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

    /**
     * A bit on the dumber side, but since you cant actually do something with a collection,
     * we are limited to a even number of identifiers, so this method is customized for our need
     * and not very dynamic
     * @param root String - top level identifier
     * @param childName String - Middle level identifier
     * @param resourceType String - Last level identifier
     * @param toUpdate Object
     * @param documentId String
     */
    private void updateNested(String root, String childName, String resourceType, Object toUpdate, String documentId) {
        db.collection(root)
                .document(childName)
                .collection(resourceType)
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

    /**
     * A bit on the dumber side, but since you cant actually do something with a collection,
     * we are limited to a even number of identifiers, so this method is customized for our need
     * and not very dynamic
     * @param root String - top level identifier
     * @param childName String - Middle level identifier
     * @param resourceType String - Last level identifier
     * @param documentId String
     */
    private void deleteNested(String root, String childName, String resourceType, String documentId) {
        db.collection(root)
                .document(childName)
                .collection(resourceType)
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

    /**
     * A bit on the dumber side, but since you cant actually do something with a collection,
     * we are limited to a even number of identifiers, so this method is customized for our need
     * and not very dynamic
     * @param root String - top level identifier
     * @param childName String - Middle level identifier
     * @param resourceType String - Last level identifier
     * @param callback {@link QuerySnapshotCallback}
     */
    private void fetchCollectionNested(String root, String childName, String resourceType,
                                         QuerySnapshotCallback callback) {
        db.collection(root)
                .document(childName)
                .collection(resourceType)
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
