package com.ntnu.wip.nabl.MVCControllers;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by markusja on 4/11/18.
 * Interface for methods related to changing view in form of
 * fragments or activities
 *
 * Contains interfaces with default implementations such that
 * an implementing class does not have to implement every
 * method
 */
public interface IChangeScreen {
    /**
     * Change screen Activity.
     */
    interface Activity {
        default void createAndLaunchNewActivity(Intent intent){}
        default void createAndLaunchNewActivity(Class activity){}
    }

    /**
     * Change screen Fragment
     */
    interface Fragment {
        default void transactionManager(Class <? extends android.support.v4.app.Fragment> frag, Bundle args) throws IllegalAccessException, InstantiationException {}
    }

}
