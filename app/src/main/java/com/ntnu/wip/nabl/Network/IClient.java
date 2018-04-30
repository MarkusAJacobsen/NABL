package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.LogEntry;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Observers.IObserverSubject;

import java.util.List;

public interface IClient extends IObserverSubject {
    /**
     * Get project
     * @param id Project id - int
     */
    void getProject(String id);

    /**
     * Write new project to cloud
     * @param project Project
     */
    void writeNewProject(Project project);

    /**
     * Update project in cloud
     * @param project Project
     */
    void updateProject(Project project);

    /**
     * Delete project from cloud
     * @param project Project
     */
    void deleteProject(Project project);

    /**
     * Get client
     * @param id Client id - Int
     */
    void getClient(String id);

    /**
     * Write new client to cloud
     * @param client Client
     */
    void writeNewClient(Client client);

    /**
     * Update client in cloud
     * @param client Client
     */
    void updateClient(Client client);

    /**
     * Delete client from cloud
     * @param client Client
     */
    void deleteClient(Client client);
    void getLogEntry(String id);
    void newLogEntry(LogEntry entry);
    void updateLogEntry(LogEntry entry);
    void deleteLogEntry(LogEntry entry);
    void newCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Company company);

    /**
     * Get all projects in cloud
     */
    void getAllProjects();

    /**
     * Get all client from cloud
     */
    void getAllClients();
    void getAllCompanies();
    void getAllLogEntries();
    void getProjectSpecificLogEntries(Project project);
    void getClientSpecificLogEntries(Client client);
}
