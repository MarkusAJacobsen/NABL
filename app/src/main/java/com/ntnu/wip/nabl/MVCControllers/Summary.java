package com.ntnu.wip.nabl.MVCControllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ntnu.wip.nabl.MVCView.SummaryView.SummaryView;
import com.ntnu.wip.nabl.R;

public class Summary extends AppCompatActivity {

    private SummaryView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = new SummaryView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        // TODO add string
        mvcView.setActionBarTitle("SUMMARY");
        setContentView(mvcView.getRootView());

    }
}
