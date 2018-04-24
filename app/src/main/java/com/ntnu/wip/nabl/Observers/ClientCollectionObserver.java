package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;

public class ClientCollectionObserver extends Observer {
    public ClientCollectionObserver(AbstractClient client) {
        subject = client;
        subject.attach(this, Subscriptions.PROJECT);
    }

    @Override
    public void update() {
        if(listener != null) {
            listener.onUpdate(subject.getClients());
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
