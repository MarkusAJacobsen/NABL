package com.ntnu.wip.nabl.Adapters.View;

import android.view.View;

public interface IProjectClientViewCard {
    void setType(String type);
    void setTitle(String title);
    void setTotalHours(String totalHours);
    void setOverTime(String overTime);
    void setHours(String hours);

    View getRootView();
}
