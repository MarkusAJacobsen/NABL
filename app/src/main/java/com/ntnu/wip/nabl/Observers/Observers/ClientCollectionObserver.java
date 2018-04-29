package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.Observer;

public class ClientCollectionObserver extends Observer {
    public ClientCollectionObserver(AbstractClient client) {
        subject = client;
        subject.attach(this);
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
