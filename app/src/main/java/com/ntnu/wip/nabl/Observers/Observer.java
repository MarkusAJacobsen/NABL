package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;

public abstract class Observer {
    protected AbstractClient subject;
    protected AddOnUpdateListener listener;
    public abstract void update(Subscriptions sub);
    public abstract void setOnUpdateListener(AddOnUpdateListener listener);

}
