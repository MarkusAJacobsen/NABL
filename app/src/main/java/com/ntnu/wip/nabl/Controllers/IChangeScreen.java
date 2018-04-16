package com.ntnu.wip.nabl.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by markusja on 4/11/18.
 */

public interface IChangeScreen {
    interface Activity {
        default void createAndLaunchNewActivity(Intent intent){}
        default void createAndLaunchNewActivity(Class activity){}
    }

}
