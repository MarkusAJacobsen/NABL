package com.ntnu.wip.nabl.MVCControllers;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by markusja on 4/11/18.
 */

public interface IChangeScreen {
    interface Activity {
        default void createAndLaunchNewActivity(Intent intent){}
        default void createAndLaunchNewActivity(Class activity){}
    }

    interface Fragment {
        default void transactionManager(Class <? extends android.support.v4.app.Fragment> frag, Bundle args) throws IllegalAccessException, InstantiationException {}
    }

}
