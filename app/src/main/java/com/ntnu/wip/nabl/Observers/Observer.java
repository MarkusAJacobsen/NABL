package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.Subscriptions;

/**
 * Generic abstract observer
 * @param <T> Extends IObserverSubject
 */
public abstract class Observer <T extends IObserverSubject> {

    /**
     * Subject to observe
     */
    protected T subject;
    protected String correlationId;
    /**
     * Change listener
     */
    protected AddOnUpdateListener listener;

    /**
     * Setter for subject
     * @param subject IObserverSubject
     */
    public abstract void setSubject(IObserverSubject subject);

    /**
     * Update without a specific {@link Subscriptions}
     */
    public abstract void update();

    /**
     * Update with a specific {@link Subscriptions}
     * @param sub
     */
    public abstract void update(Subscriptions sub);

    /**
     * Setter for listener
     * @param listener {@link AddOnUpdateListener}
     */
    public abstract void setOnUpdateListener(AddOnUpdateListener listener);

    /**
     * Adds a correlation ID to the observer
     * @param correlationId
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

}
