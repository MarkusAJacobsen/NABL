package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

/**
 * Observer for project collection change
 */
public class ProjectCollectionObserver extends Observer<AbstractClient> {
    protected ProjectCollectionObserver() {}

    @Override
    public void setSubject(IObserverSubject subject) {
        this.subject = (AbstractClient) subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        //NO-OP
    }

    @Override
    public void update(Subscriptions sub) {
        if(listener != null) {
            if(sub == Subscriptions.PROJECTS) {
                listener.onUpdate(subject.getProjects());
            }
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
