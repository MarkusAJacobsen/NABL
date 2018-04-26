package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class ProjectObserver extends Observer {
    public ProjectObserver(AbstractClient client) {
        subject = client;
        subject.attach(this);
    }

    @Override
    public void update(Subscriptions sub) {
        if(listener != null) {
            if(sub == Subscriptions.PROJECT_SINGULAR) {
                Project pro = subject.getLastFetchedProject();
                listener.onUpdate(pro);
            }
        }
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
