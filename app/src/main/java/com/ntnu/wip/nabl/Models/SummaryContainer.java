package com.ntnu.wip.nabl.Models;


/**
 * This class will create a wrapper arround project and client.
 * Wrapping the objects as well as store relevant information
 * about hours spent
 */

public class SummaryContainer {
    private long overTime;
    private long hours;
    Object entity;
    ContentType type;



    public enum ContentType {
        PROJECT,
        CLIENT;
    }

    SummaryContainer(Project project) {
        entity = project;
        type = ContentType.PROJECT;
    }
}
