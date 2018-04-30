package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Authentication.IAuthentication;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

/**
 * Observer for when a logout event happens
 */
public class SignOutObserver extends Observer<IAuthentication> {
    protected SignOutObserver() {}

    @Override
    public void setSubject(IObserverSubject subject) {
        this.subject = (IAuthentication) subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        if(listener != null) {
            listener.onUpdate(subject.getCurrentUser());
        }
    }

    @Override
    public void update(Subscriptions sub) {
        //NO-OP
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }
}
