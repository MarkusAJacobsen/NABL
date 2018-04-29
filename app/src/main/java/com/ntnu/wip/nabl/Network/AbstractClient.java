package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClient implements IClient {
    private List<Observer> observers = new ArrayList<>();

    private List<Project> projects;
    private List<Client> clients;
    private Project lastFetchedProject;
    private Client lastFetchedClient;

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
}
