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
        CLIENT
    }

    SummaryContainer(Project project) {
        entity = project;
        type = ContentType.PROJECT;
    }

    SummaryContainer(Client client) {
        entity = client;
        type = ContentType.CLIENT;
    }


    public long getHours() {
        return hours;
    }

    public long getOverTime() {
        return overTime;
    }

    public Object getEntity() {
        return entity;
    }

    public ContentType getType() {
        return type;
    }

    public long getTotalHours() {
        return overTime+hours;
    }

    public String getTitleString() {
        switch (type) {
            case CLIENT:
                Client c = (Client) entity;
                return c.getName();

            case PROJECT:
                Project p = (Project) entity;
                return p.getName();

                default:
                    return "OH FUCK. OH FUCK. OH FUCK";

        }
    }

    public String getId() {
        switch (type) {
            case PROJECT:
                Project p = (Project) entity;
                return p.getId();

            case CLIENT:
                Client c = (Client) entity;
                return c.getId();

                default:
                    return "SHOULD NEVER HAPPEN :o";
        }
    }

    public void addWorkDay(WorkDay day) {
        hours += day.getTotal();
        overTime += day.getOverTime();
    }
}
