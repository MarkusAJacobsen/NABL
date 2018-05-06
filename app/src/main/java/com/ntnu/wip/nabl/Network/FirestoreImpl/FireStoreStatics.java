package com.ntnu.wip.nabl.Network.FirestoreImpl;

/**
 * Firestore database fields and collections names
 */
public interface FireStoreStatics {
    // Collections
    String PROJECT_COLLECTION = "projects";
    String CLIENT_COLLECTION = "clients";
    String LOG_ENTRY_COLLECTION = "logEntries";
    String COMPANIES_COLLECTION = "companies";
    String ROOT_LEVEL_DATA = "data";

    // Fields
    String LOG_ENTRY_USER_ID = "userId";
    String LOG_ENTRY_CLIENT = "clientId";
    String LOG_ENTRY_PROJECT = "projectId";
    String LOG_ENTRY_START_FIELD = "startTimeInMillis";
    String LOG_ENTRY_STOP_FIELD = "endTimeInMillis";

    String COMPANY_USER_ID = "ownerId";
    String COMPANY_PROJECT_FIELD = "companyId";
    String COMPANY_CLIENT_FIELD = "";
}
