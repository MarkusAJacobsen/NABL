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

    public CompanyContainer() {
        clientsProjects = new ArrayList<>();
    }

    public CompanyContainer(Company company) {
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

    public void setClientList(List<Client> clients) {
        for (Client client: clients) {
            clientsProjects.add(new SummaryContainer(client));
        }
    }

    public void setProjectList(List<Project> projects) {
        for (Project project : projects) {
            clientsProjects.add(new SummaryContainer(project));
        }
    }

    public void setProject(Project project) {
        clientsProjects.add(new SummaryContainer(project));
    }

    public void setClient(Client client) {
        clientsProjects.add(new SummaryContainer(client));
    }

    public void addWorkDays(List<WorkDay> workDays) {
        for (WorkDay day: workDays) {
            for (SummaryContainer container: clientsProjects) {
                if (container.getId().equals(day.getClientId()) || container.getId().equals(day.getProjectId()))  {
                    container.addWorkDay(day);
                    break;
                }
            }
        }
    }
}
