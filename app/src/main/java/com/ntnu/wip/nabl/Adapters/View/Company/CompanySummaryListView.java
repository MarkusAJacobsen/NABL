package com.ntnu.wip.nabl.Adapters.View.Company;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ntnu.wip.nabl.Adapters.ClientProjectAdapter;
import com.ntnu.wip.nabl.Adapters.CompanyAdapter;
import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanySummaryListView implements ICompanySummaryView {

    private View rootView;

    @BindView(R.id.companyName)
    TextView companyName;

    @BindView(R.id.projects_and_clients)
    ListView listView;

    public CompanySummaryListView(LayoutInflater inflater, ViewGroup group) {
        rootView = inflater.inflate(R.layout.company_summary_card, null);
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void setAdapter(ClientProjectAdapter adapter) {
       listView.setAdapter(adapter);
       adapter.notifyDataSetChanged();
    }

    @Override
    public void setCompanyName(String name) {
        companyName.setText(name);
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
