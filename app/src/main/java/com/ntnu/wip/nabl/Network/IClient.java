package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.LogEntry;
import com.ntnu.wip.nabl.Models.Project;

import java.util.List;

public interface IClient {
    void registerListener();
    void writeNewProject(Project project);
    void updateProject(Project project);
    void deleteProject(Project project);
    void writeNewClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
    void newLogEntry(LogEntry entry);
    void updateLogEntry(LogEntry entry);
    void deleteLogEntry(LogEntry entry);
    void newCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Company company);
    List<Project> getAllProjects();
    List<Client> getAllClients();
    List<Company> getAllCompanies();
    List<LogEntry> getAllLogEntries();
    List<LogEntry> getProjectSpecificLogEntries(Project project);
    List<LogEntry> getClientSpecificLogEntries(Client client);
}
