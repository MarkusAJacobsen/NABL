package com.ntnu.wip.nabl.Models;

import android.content.Context;

import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

public enum State {
    PLANNING,
    STARTED,
    FINISHED;

    private static final int SIZE = 3;

    public static int size(){
        return SIZE;
    }

    public static State get(int i) {
        switch (i) {
            case 0: return PLANNING;
            case 1: return STARTED;
            case 2: return FINISHED;
            default: return null;
        }
    }

    public static String getName(int i, Context context) {
        switch (i) {
            case 0: return context.getString(R.string.planning);
            case 1: return context.getString(R.string.started);
            case 2: return context.getString(R.string.finished);
            default: return null;
        }
    }

    public static String getName(State state, Context context) {
        switch (state) {
            case PLANNING: return context.getString(R.string.planning);
            case STARTED: return context.getString(R.string.started);
            case FINISHED: return context.getString(R.string.finished);
            default: return null;
        }
    }

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
