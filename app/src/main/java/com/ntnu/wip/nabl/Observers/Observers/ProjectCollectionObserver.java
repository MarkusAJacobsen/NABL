package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.List;

public class ProjectCollectionObserver extends Observer {
    public ProjectCollectionObserver(AbstractClient client) {
        subject = client;
        subject.attach(this, Subscriptions.PROJECT);
    }

    @Override
    public void update() {
        if(listener != null) {
            listener.onUpdate(subject.getProjects());
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
