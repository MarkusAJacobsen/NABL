package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

public class LogObserver extends Observer<AbstractClient> {
    protected LogObserver() {}

    @Override
    public void setSubject(IObserverSubject subject) {
        this.subject = (AbstractClient) subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        //No op
    }

    @Override
    public void update(Subscriptions sub) {
        if(listener != null && sub == Subscriptions.LOG_ENTRIES) {
            listener.onUpdate(subject.getLastFetchedWorkdays());
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
