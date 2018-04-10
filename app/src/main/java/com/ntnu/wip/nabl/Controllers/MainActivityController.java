package com.ntnu.wip.nabl.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ntnu.wip.nabl.MVCView.MainActivity.MainActivityView;

public class MainActivityController extends AppCompatActivity {
    MainActivityView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new MainActivityView(getLayoutInflater(), null);

        setContentView(mvcView.getRootView());
    }
}
