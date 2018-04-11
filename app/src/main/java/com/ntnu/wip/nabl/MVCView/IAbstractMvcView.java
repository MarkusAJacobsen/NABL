package com.ntnu.wip.nabl.MVCView;

import android.support.v7.app.ActionBar;
import android.view.View;

/**
 * Created by markusja on 4/10/18.
 */

public interface IAbstractMvcView {
    View getRootView();
    void setActionBar(ActionBar actionbar);
    void setActionBarTitle(String title);
}
