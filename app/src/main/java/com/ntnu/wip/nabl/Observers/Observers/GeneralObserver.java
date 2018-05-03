package com.ntnu.wip.nabl.Observers.Observers;

import com.ntnu.wip.nabl.Network.AbstractClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.List;
import java.util.Map;

public class GeneralObserver extends Observer<AbstractClient> {
    protected GeneralObserver() {}

    @Override
    public void setSubject(IObserverSubject subject) {
        this.subject =  (AbstractClient) subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        for (Map.Entry<String, List<Object>> entry: subject.getDeliveryQueue().entrySet()) {
            if (entry.getKey().equals(correlationId)) {
                if (listener != null) {
                    listener.onUpdate(subject.getDeliveryQueue().remove(entry.getKey()));
                }
            }
        }
    }

    @Override
    public void update(Subscriptions sub) {
        // NO OP
    }

    @Override
    public void setOnUpdateListener(AddOnUpdateListener listener) {
        this.listener = listener;
    }

}
