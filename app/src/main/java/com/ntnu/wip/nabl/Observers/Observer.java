package com.ntnu.wip.nabl.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;

public abstract class Observer {
    AbstractClient subject;
    AddOnUpdateListener listener;
    public abstract void update();
    public abstract void setOnUpdateListener(AddOnUpdateListener listener);

}
