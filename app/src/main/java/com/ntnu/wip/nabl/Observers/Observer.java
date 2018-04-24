package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;

public abstract class Observer {
    protected AbstractClient subject;
    protected AddOnUpdateListener listener;
    public abstract void update();
    public abstract void setOnUpdateListener(AddOnUpdateListener listener);

}
