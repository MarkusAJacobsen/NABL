package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.LogEntry;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.User;
import com.ntnu.wip.nabl.Models.WorkDay;

import java.util.List;

public interface IClient {
    void getProject(String id);
    void writeNewProject(Project project);
    void updateProject(Project project);
    void deleteProject(Project project);
    void getClient(String id);
    void writeNewClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
    void getLogEntry(String id);
    void newLogEntry(WorkDay workDay);
    void getLogEntriesByUser(User user);
    void updateLogEntry(LogEntry entry);
    void deleteLogEntry(LogEntry entry);
    void newCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Company company);
    void getAllProjects();
    void getAllClients();
    void getAllCompanies();
    void getAllLogEntries();
    void getProjectSpecificLogEntries(Project project);
    void getClientSpecificLogEntries(Client client);
}
