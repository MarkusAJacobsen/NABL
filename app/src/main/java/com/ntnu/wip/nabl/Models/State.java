package com.ntnu.wip.nabl.Models;

import android.content.Context;

import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * State represents the state of something, in our case a project
 */
public enum State {
    PLANNING,
    STARTED,
    FINISHED;

    private static final int SIZE = 3;

    /**
     * Return enum size
     * @return int
     */
    public static int size(){
        return SIZE;
    }

    /**
     * Get state based in an position
     * @param position int
     * @return State
     */
    public static State get(int position) {
        switch (position) {
            case 0: return PLANNING;
            case 1: return STARTED;
            case 2: return FINISHED;
            default: return null;
        }
    }

    /**
     * Get name of an State based on position, with concern to i18n
     * @param position int
     * @param context Context
     * @return String
     */
    public static String getName(int position, Context context) {
        switch (position) {
            case 0: return context.getString(R.string.planning);
            case 1: return context.getString(R.string.started);
            case 2: return context.getString(R.string.finished);
            default: return null;
        }
    }

    /**
     * Get name of an state based on name, with concern to i18n
     * @param state State
     * @param context Context
     * @return String
     */
    public static String getName(State state, Context context) {
        switch (state) {
            case PLANNING: return context.getString(R.string.planning);
            case STARTED: return context.getString(R.string.started);
            case FINISHED: return context.getString(R.string.finished);
            default: return null;
        }
    }

    /**
     * Get State based on a String
     * @param value String
     * @param context Context
     * @return State
     */
    public static State getState(String value, Context context) {
        List<String> options = new ArrayList<>();
        options.add(context.getString(R.string.planning));
        options.add(context.getString(R.string.started));
        options.add(context.getString(R.string.finished));

        int i = 0;
        for(String option : options) {
            if(value.equals(option)){
                return get(i);
            }
            ++i;
        }

        return null;
    }
}
