package com.ntnu.wip.nabl.Controllers;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by markusja on 4/11/18.
 */

public interface IChangeScreen {
    interface Activity {
        void createAndLaunchNewActivity(Class activity);
    }

}
