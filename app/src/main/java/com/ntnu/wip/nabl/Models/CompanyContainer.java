package com.ntnu.wip.nabl.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is made to create a thin wrapper around
 * the construct of "company"
 */
public class CompanyContainer {

    private Company company;
    private List<SummaryContainer> clientsProjects;

    CompanyContainer(Company company) {
        this.company = company;
        clientsProjects = new ArrayList<>();
    }

    public void addSummaryObject(SummaryContainer summaryContainer) {
        clientsProjects.add(summaryContainer);
    }

    public Company getCompany() {
        return company;
    }

    public List<SummaryContainer> getClientsProjects() {
        return clientsProjects;
    }

    public void setClientsProjects(List<SummaryContainer> clientsProjects) {
        this.clientsProjects = clientsProjects;
    }
}
