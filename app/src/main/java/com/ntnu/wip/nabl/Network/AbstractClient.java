package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Models.WorkDay;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClient implements IClient {
    private List<Observer> observers = new ArrayList<>();

    private List<Project> projects;
    private List<Client> clients;
    private Project lastFetchedProject;
    private Client lastFetchedClient;
    private List<WorkDay> lastFetchedWorkdays;
    private WorkDay lastFetchedWorkday;
    private List<Company> lastFetchedCompanies;
    private List<Project> lastFetchedProjects;
    private List<Client> lastFetchedClients;

    public List<Project> getProjects() {
        return projects;
    }

    public List<Client> getClients() {
        return clients;
    }

    protected void setProjects(List<Project> projects) {
        this.projects = projects;
        notifyAllObservers(Subscriptions.PROJECTS);
    }

    protected void setClients(List<Client> clients) {
        this.clients = clients;
        notifyAllObservers(Subscriptions.CLIENTS);
    }

    public Project getLastFetchedProject() {
        return lastFetchedProject;
    }

    protected void setLastFetchedProject(Project lastFetchedProject) {
        this.lastFetchedProject = lastFetchedProject;
        notifyAllObservers(Subscriptions.PROJECT_SINGULAR);
    }

    public Client getLastFetchedClient() {
        return lastFetchedClient;
    }

    protected void setLastFetchedClient(Client lastFetchedClient) {
        this.lastFetchedClient = lastFetchedClient;
        notifyAllObservers(Subscriptions.CLIENT_SINGULAR);
    }

    @Override
    public void attach(Observer observer){
        observers.add(observer);
    }

    private void notifyAllObservers(Subscriptions sub) {
        for (Observer observer : observers) {
            observer.update(sub);
        }
    }

    public void setLastFetchedWorkday(WorkDay lastFetchedWorkday) {
        this.lastFetchedWorkday = lastFetchedWorkday;
    }

    public void setLastFetchedWorkdays(List<WorkDay> lastFetchedWorkdays) {
        this.lastFetchedWorkdays = lastFetchedWorkdays;
        notifyAllObservers(Subscriptions.LOG_ENTRIES);
    }

    public void setLastFetchedCompanies(List<Company> lastFetchedCompanies) {
        this.lastFetchedCompanies = lastFetchedCompanies;
        notifyAllObservers(Subscriptions.COMPANIES);
    }

    public void setLastFetchedClients(List<Client> lastFetchedClients) {
        this.lastFetchedClients = lastFetchedClients;
    }

    public void setLastFetchedProjects(List<Project> lastFetchedProjects) {
        this.lastFetchedProjects = lastFetchedProjects;
    }

    public List<Client> getLastFetchedClients() {
        return lastFetchedClients;
    }

    public List<Company> getLastFetchedCompanies() {
        return lastFetchedCompanies;
    }

    public List<Project> getLastFetchedProjects() {
        return lastFetchedProjects;
    }

    public List<WorkDay> getLastFetchedWorkdays() {
        return lastFetchedWorkdays;
    }
}
