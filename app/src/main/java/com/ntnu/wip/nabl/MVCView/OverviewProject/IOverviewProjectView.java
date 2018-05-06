package com.ntnu.wip.nabl.MVCView.OverviewProject;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Overview project view method contract
 */
public interface IOverviewProjectView extends IAbstractMvcView {
    /**
     * Set text in field 'ProjectId'
     * @param projectId String
     */
    void setProjectId(String projectId);

    /**
     * Set text in field 'Address'
     * @param address String
     */
    void setAddress(String address);

    /**
     * Set text in field 'Name'
     * @param name String
     */
    void setProjectName(String name);

    /**
     * Set text in field 'State'
     * @param state String
     */
    void setState(String state);

    /**
     * Set text in field 'Description'
     * @param description String
     */
    void setDescription(String description);

    /**
     * Set text in field 'Start'
     * @param start String
     */
    void setStart(String start);

    /**
     * Set text in field 'End'
     * @param end String
     */
    void setEnd(String end);
}
