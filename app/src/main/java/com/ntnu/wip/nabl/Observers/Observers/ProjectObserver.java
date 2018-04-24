package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.List;

public class ProjectObserver extends Observer {
    public ProjectObserver(AbstractClient client) {
        subject = client;
        subject.attach(this, Subscriptions.PROJECT_SINGULAR);
    }

    @Override
    public void update() {
        if(listener != null) {
            listener.onUpdate((List) subject.getLastFetchedProject());
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
