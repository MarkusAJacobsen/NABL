package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class ClientObserver extends Observer<AbstractClient> {
    protected ClientObserver() {}

    @Override
    public void setSubject(IObserverSubject subject) {
        this.subject = (AbstractClient) subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        //NO_OP
    }

    @Override
    public void update(Subscriptions sub) {
        if(listener != null) {
            if(sub == Subscriptions.CLIENT_SINGULAR) {
                Client cli = subject.getLastFetchedClient();
                listener.onUpdate(cli);
            }
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
