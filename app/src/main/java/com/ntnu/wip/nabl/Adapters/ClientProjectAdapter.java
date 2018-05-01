package com.ntnu.wip.nabl.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Project;

import java.util.List;

/**
 * This adapter takes two different kinds of objects
 * It treats them both as equals and creates one list of elements.
 * Projects are represented in the first indexes while clients are last
 * Created by klingen on 01.05.18.
 */

public class ClientProjectAdapter extends BaseAdapter {

    private List<Client> clientList;
    private List<Project> projectList;

    public void setClientList(List<Client> clients) {
        clientList = clients;
        notifyDataSetChanged();
    }

    public void setProjectList(List<Project> projects) {
        projectList = projects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return clientList.size()+projectList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= projectList.size()) {
            return clientList.get(i-projectList.size());
        } else {
            return clientList.get(i);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new View();
    }
}
