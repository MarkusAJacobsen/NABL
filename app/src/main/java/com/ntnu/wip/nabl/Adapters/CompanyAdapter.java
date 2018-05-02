package com.ntnu.wip.nabl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ntnu.wip.nabl.Adapters.View.Company.CompanySummaryListView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.CompanyContainer;

import java.util.List;

/**
 * Created by klingen on 01.05.18.
 */

public class CompanyAdapter extends BaseAdapter {

    private List<CompanyContainer> companies;
    private Context context;
    CompanyAdapter(Context context, List<CompanyContainer> list) {
        companies = list;
        this.context = context;
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

        CompanySummaryListView mvcView = new CompanySummaryListView(inflater, parent);
        mvcView.setCompanyName(companies.get(position).getCompany().getName());
        mvcView.setAdapter(clientProjectAdapter);
        return mvcView.getRootView();
    }
}
