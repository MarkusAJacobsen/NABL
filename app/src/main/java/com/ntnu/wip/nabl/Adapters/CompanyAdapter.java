package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ntnu.wip.nabl.Adapters.View.Company.CompanySummaryListView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.CompanyContainer;
import com.ntnu.wip.nabl.R;

import java.util.List;

/**
 * Created by klingen on 01.05.18.
 */

public class CompanyAdapter extends BaseAdapter {

    private List<CompanyContainer> companies;
    private Context context;
    public CompanyAdapter(Context context, List<CompanyContainer> list) {
        companies = list;
        this.context = context;
    }

    public void addData(List<CompanyContainer> newCompanies) {
        companies.addAll(newCompanies);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Object getItem(int position) {
        return companies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ClientProjectAdapter clientProjectAdapter = new ClientProjectAdapter(context, companies.get(position).getClientsProjects());
        convertView = inflater.inflate(R.layout.company_summary_card, null);
        TextView name = convertView.findViewById(R.id.companyName);
        name.setText(companies.get(position).getCompany().getName());
        ListView projectClientListView = convertView.findViewById(R.id.projects_and_clients_list);
        projectClientListView.setOnTouchListener(new View.OnTouchListener() {

            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        ClientProjectAdapter cpa = new ClientProjectAdapter(context, companies.get(position).getClientsProjects());
        projectClientListView.setAdapter(cpa);

        return convertView;
    }
}
