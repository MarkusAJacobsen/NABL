package com.ntnu.wip.nabl.MVCView.OverviewProject;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IOverviewProjectView extends IAbstractMvcView {
    void setProjectId(String projectId);
    void setAddress(String address);
    void setProjectName(String name);
    void setState(String state);
    void setDescription(String description);
    void setStart(String start);
    void setEnd(String end);
}
