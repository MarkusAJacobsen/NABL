package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

public class ClientCollectionObserver extends Observer<AbstractClient> {
    protected ClientCollectionObserver() {}

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
            if (sub == Subscriptions.CLIENTS) {
                listener.onUpdate(subject.getClients());
            }
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
