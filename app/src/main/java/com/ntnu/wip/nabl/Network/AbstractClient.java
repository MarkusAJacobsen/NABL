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

/**
 * Abstract network client. Hold all logic for data storing, and observer triggering
 */
public abstract class AbstractClient implements IClient {
    private List<Observer> observers = new ArrayList<>();

    private List<Project> projects;
    private List<Client> clients;
    private Project lastFetchedProject;
    private Client lastFetchedClient;
    private List<WorkDay> lastFetchedWorkdays;
    private WorkDay lastFetchedWorkday;
    private List<Company> lastFetchedCompanies;

    /**
     * Getter for projects
     * @return List<Project>
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Getter for clients
     * @return List<Client>
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Setter for projects
     * @param projects List<Project>
     */
    protected void setProjects(List<Project> projects) {
        this.projects = projects;
        notifyAllObservers(Subscriptions.PROJECTS);
    }

    /**
     * Setter for clients
     * @param clients List<Client>
     */
    protected void setClients(List<Client> clients) {
        this.clients = clients;
        notifyAllObservers(Subscriptions.CLIENTS);
    }

    /**
     * Getter for lastFetchedProject
     * @return {@link Project}
     */
    public Project getLastFetchedProject() {
        return lastFetchedProject;
    }

    /**
     * Setter for lastFetchedProject
     * @param lastFetchedProject {@link Project}
     */
    protected void setLastFetchedProject(Project lastFetchedProject) {
        this.lastFetchedProject = lastFetchedProject;
        notifyAllObservers(Subscriptions.PROJECT_SINGULAR);
    }

    /**
     * Getter for lastFetchedClient
     * @return {@link Client}
     */
    public Client getLastFetchedClient() {
        return lastFetchedClient;
    }

    /**
     * Setter for lastFetchedClient
     * @param lastFetchedClient {@link Client}
     */
    protected void setLastFetchedClient(Client lastFetchedClient) {
        this.lastFetchedClient = lastFetchedClient;
        notifyAllObservers(Subscriptions.CLIENT_SINGULAR);
    }

    /**
     * Attach an observer
     * @param observer Observer
     */
    @Override
    public void attach(Observer observer){
        observers.add(observer);
    }

    /**
     * Notify all observers
     * @param sub {@link Subscriptions} - Specify which event you are sending out
     */
    private void notifyAllObservers(Subscriptions sub) {
        for (Observer observer : observers) {
            observer.update(sub);
        }
    }

    /**
     * Setter for lastFetchedWorkDay
     * @param lastFetchedWorkday {@link WorkDay}
     */
    protected void setLastFetchedWorkday(WorkDay lastFetchedWorkday) {
        this.lastFetchedWorkday = lastFetchedWorkday;
    }

    /**
     * Setter for lastFetchedWorkDays
     * @param lastFetchedWorkdays List<WorkDay>
     */
    protected void setLastFetchedWorkdays(List<WorkDay> lastFetchedWorkdays) {
        this.lastFetchedWorkdays = lastFetchedWorkdays;
        notifyAllObservers(Subscriptions.LOG_ENTRIES);
    }

    /**
     * Setter for lastFetchedCompanies
     * @param lastFetchedCompanies List<Company>
     */
    protected void setLastFetchedCompanies(List<Company> lastFetchedCompanies) {
        this.lastFetchedCompanies = lastFetchedCompanies;
        notifyAllObservers(Subscriptions.COMPANIES);
    }

    /**
     * Getter for lastFetchedCompanies
     * @return List<Company>
     */
    public List<Company> getLastFetchedCompanies() {
        return lastFetchedCompanies;
    }

    /**
     * Getter for lastFetchedWorkDays
     * @return List<WorkDay>
     */
    public List<WorkDay> getLastFetchedWorkdays() {
        return lastFetchedWorkdays;
    }
}
