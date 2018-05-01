package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.LogEntry;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.Observers.IObserverSubject;

import java.util.List;

/**
 * Interface for an Network component. All network components must comply to
 * this structure to ensure workings of the system
 */
public interface IClient extends IObserverSubject {
    /**
     * Get project based on Company
     * @param id Project id - int
     */
    void getProject(String id) throws CompanyNotFoundException;

    /**
     * Write new project to cloud based on Company
     * @param project Project
     */
    void writeNewProject(Project project) throws CompanyNotFoundException;

    /**
     * Update project in cloud based on Company
     * @param project Project
     */
    void updateProject(Project project) throws CompanyNotFoundException;

    /**
     * Delete project from cloud based on Company
     * @param project Project
     */
    void deleteProject(Project project) throws CompanyNotFoundException;

    /**
     * Get client based on Company
     * @param id Client id - Int
     */
    void getClient(String id) throws CompanyNotFoundException;

    /**
     * Write new client to cloud based on Company
     * @param client Client
     */
    void writeNewClient(Client client) throws CompanyNotFoundException;

    /**
     * Update client in cloud based on Company
     * @param client Client
     */
    void updateClient(Client client) throws CompanyNotFoundException;

    /**
     * Delete client from cloud based on Company
     * @param client Client
     */
    void deleteClient(Client client) throws CompanyNotFoundException;
    void getLogEntry(String id);
    void newLogEntry(WorkDay workDay);
    void getLogEntries(String uid, String cid, String pid, long startMillis, long stopMillis);
    void getLogEntriesByUser(User user);
    void newCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Company company);

    void getUserCompanies(String uid);

    /**
     * Get all projects in cloud based on Company
     */
    void getAllProjects() throws CompanyNotFoundException;

    /**
     * Get all client from cloud based on Company
     */
    void getAllClients() throws CompanyNotFoundException;
    void getAllCompanies();
    void getAllLogEntries();
    void getProjectSpecificLogEntries(Project project);
    void getClientSpecificLogEntries(Client client);
}
