package com.ntnu.wip.nabl.Observers;

/**
 * Interface for subjects in Observers
 */
public interface IObserverSubject {
    /**
     * Attach an observer
     * @param observer Observer
     */
    void attach(Observer observer);
}
