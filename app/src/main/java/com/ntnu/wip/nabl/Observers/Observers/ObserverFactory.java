package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Observers.Observer;

public final class ObserverFactory {
    private ObserverFactory(){}

    public static final int CLIENT_COLLECTION = 0;
    public static final int CLIENT = 1;
    public static final int PROJECT_COLLECTION = 2;
    public static final int PROJECT = 3;
    public static final int SIGN_OUT = 4;
    public static final int FETCH_COMPANIES_CLIENTS_PROJECTS = 5;


    public static Observer create(int observerType) {
        switch (observerType) {
            case CLIENT_COLLECTION: return new ClientCollectionObserver();
            case CLIENT: return new ClientObserver();
            case PROJECT_COLLECTION: return new ProjectCollectionObserver();
            case PROJECT: return new ProjectObserver();
            case SIGN_OUT: return new SignOutObserver();
            case FETCH_COMPANIES_CLIENTS_PROJECTS: return new SummaryControllerObserver();
            default: return null;
        }
    }
}
