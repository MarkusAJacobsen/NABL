package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.IClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class ProjectObserver extends Observer<AbstractClient> {
    protected ProjectObserver() {}

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
