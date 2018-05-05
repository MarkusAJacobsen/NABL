package com.ntnu.wip.nabl.MVCView.SummaryView;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ntnu.wip.nabl.Adapters.CompanyAdapter;
import com.ntnu.wip.nabl.Adapters.CompanyListAdapter;
import com.ntnu.wip.nabl.MVCView.Settings.ISettingsView;
import com.ntnu.wip.nabl.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by klingen on 01.05.18.
 */

public class SummaryView implements ISummaryView {
    private View rootView;
    private ActionBar actionBar;

    @BindView(R.id.summary_listView)
    ListView companyListView;

    public SummaryView(LayoutInflater inflater, ViewGroup group) {

        rootView = inflater.inflate(R.layout.activity_summary, group);
        ButterKnife.bind(this, rootView);
    }


    @Override
    public void setActionBar(ActionBar actionbar) {
        this.actionBar = actionbar;
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String title) {
        actionBar.setTitle(title);
    }


    @Override
    public View getRootView() {
        return rootView;
    }


    @Override
    public void setCompanyAdapters(CompanyAdapter adapters) {
        companyListView.setAdapter(adapters);
        adapters.notifyDataSetChanged();
    }
}
