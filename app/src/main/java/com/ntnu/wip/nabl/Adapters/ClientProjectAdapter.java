package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.Project;
import com.ntnu.wip.nabl.R;

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
    private Context context;

    ClientProjectAdapter(Context context, Company company) {
        this.context = context;
    }

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
            return clientList.get(position-projectList.size());
        } else {
            return clientList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.project_client_list_element, null);

        TextView indicator = convertView.findViewById(R.id.project_client_indicator);
        TextView indicator_name = convertView.findViewById(R.id.indicator_name);

        TextView cardHours = convertView.findViewById(R.id.cardHours);
        TextView cardOverTime = convertView.findViewById(R.id.cardOverTime);
        TextView cardTotalHours = convertView.findViewById(R.id.cardHoursTotal);


        if ()

        return convertView;
    }
}
