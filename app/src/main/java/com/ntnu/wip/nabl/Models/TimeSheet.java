package com.ntnu.wip.nabl.Models;

import java.util.List;

/**
 * Created by klingen on 26.04.18.
 * This class will explain the basics of a timesheet
 * Data in this class should be enough to create a complete timesheet.
 */

public class TimeSheet {
    private Company company;    // A timesheet is for a company
    private Client client;      // Timesheet is made by a client
    private List<WorkDay> workDays;     // Some days work

    TimeSheet(Company company, Client client, List<WorkDay> workDays) {
        this.client = client;
        this.company = company;
        this.workDays = workDays;
    }



}
