package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;

public abstract class Observer <T extends IObserverSubject> {
    protected T subject;
    protected AddOnUpdateListener listener;
    public abstract void setSubject(IObserverSubject subject);
    public abstract void update();
    public abstract void update(Subscriptions sub);
    public abstract void setOnUpdateListener(AddOnUpdateListener listener);

}
