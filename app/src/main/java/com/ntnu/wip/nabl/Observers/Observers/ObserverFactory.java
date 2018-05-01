package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Observers.Observer;

/**
 * Class for returning an instance of {@link Observer}
 */
public final class ObserverFactory {
    private ObserverFactory(){}

    /**
     * Modes
     */
    public static final int CLIENT_COLLECTION = 0;
    public static final int CLIENT = 1;
    public static final int PROJECT_COLLECTION = 2;
    public static final int PROJECT = 3;
    public static final int SIGN_OUT = 4;

    /**
     * Create returns an {@link Observer} specificed by the sent in observerMode, see modes above
     * @param observerType int
     * @return {@link Observer}
     */
    public static Observer create(int observerType) {
        switch (observerType) {
            case CLIENT_COLLECTION: return new ClientCollectionObserver();
            case CLIENT: return new ClientObserver();
            case PROJECT_COLLECTION: return new ProjectCollectionObserver();
            case PROJECT: return new ProjectObserver();
            case SIGN_OUT: return new SignOutObserver();
            default: return null;
        }
    }
}
