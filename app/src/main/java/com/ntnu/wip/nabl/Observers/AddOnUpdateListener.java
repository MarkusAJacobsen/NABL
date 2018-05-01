package com.ntnu.wip.nabl.Observers;

import java.util.List;

/**
 * Observer callback
 * @param <T> Any
 */
public interface AddOnUpdateListener<T> {

    /**
     * On update
     * @param obj T
     */
    void onUpdate(T obj);
}
