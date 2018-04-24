package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class ClientObserver extends Observer {
    public ClientObserver(AbstractClient client) {
        subject = client;
        subject.attach(this, Subscriptions.CLIENT_SINGULAR);
    }

    @Override
    public void update() {
        if(listener != null) {
            List<Client> foo = new ArrayList<>();
            foo.add(subject.getLastFetchedClient());
            listener.onUpdate(foo);
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
