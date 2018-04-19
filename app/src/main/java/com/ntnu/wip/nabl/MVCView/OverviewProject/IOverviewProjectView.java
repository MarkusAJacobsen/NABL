package com.ntnu.wip.nabl.MVCView.OverviewProject;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IOverviewProjectView extends IAbstractMvcView {
    void setProjectId(String projectId);
    void setAddress(String address);
}
