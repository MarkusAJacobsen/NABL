package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClient implements IClient {
    private List<Observer> projectObservers = new ArrayList<>();
    private List<Observer> clientObservers = new ArrayList<>();
    private List<Observer> singluarProjectObserver = new ArrayList<>();
    private List<Observer> singularClientObserver = new ArrayList<>();

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
        notifyAllProjectObservers();
    }

    protected void setClients(List<Client> clients) {
        this.clients = clients;
        notifyAllClientObservers();
    }

    public Project getLastFetchedProject() {
        return lastFetchedProject;
    }

    public void setLastFetchedProject(Project lastFetchedProject) {
        this.lastFetchedProject = lastFetchedProject;
        notifyAllSingularProjectObservers();
    }

    public Client getLastFetchedClient() {
        return lastFetchedClient;
    }

    public void setLastFetchedClient(Client lastFetchedClient) {
        this.lastFetchedClient = lastFetchedClient;
        notifyAllSingularClientObservers();
    }

    public void attach(Observer observer, Subscriptions subscription){
        switch (subscription) {
            case PROJECT: projectObservers.add(observer); break;
            case CLIENT: clientObservers.add(observer); break;
            case PROJECT_SINGULAR: singluarProjectObserver.add(observer); break;
            case CLIENT_SINGULAR: singularClientObserver.add(observer); break;
            default: break; //Default drop request
        }
    }

    private void notifyAllProjectObservers(){
        for (Observer observer : projectObservers) {
            observer.update();
        }
    }

    private void notifyAllClientObservers(){
        for (Observer observer : projectObservers) {
            observer.update();
        }
    }

    private void notifyAllSingularProjectObservers(){
        for (Observer observer : singluarProjectObserver) {
            observer.update();
        }
    }

    private void notifyAllSingularClientObservers(){
        for (Observer observer : singularClientObserver) {
            observer.update();
        }
    }
}
