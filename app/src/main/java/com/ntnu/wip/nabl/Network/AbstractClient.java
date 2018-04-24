package com.ntnu.wip.nabl.Network;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClient implements IClient {
    private List<Observer> projectObservers = new ArrayList<>();
    private List<Observer> clientObservers = new ArrayList<>();

    private List<Project> projects;
    private List<Client> clients;

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

    public void attach(Observer observer, Subscriptions subscription){
        switch (subscription) {
            case PROJECT: projectObservers.add(observer); break;
            case CLIENT: clientObservers.add(observer); break;
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
}
