package com.ntnu.wip.nabl.MVCView.MainActivity;

import android.app.Activity;
import android.support.v7.app.ActionBarDrawerToggle;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Created by markusja on 4/10/18.
 */

public interface IMainActivityView extends IAbstractMvcView {
    void createSideMenu(Activity target);
    void syncDrawer();
}
