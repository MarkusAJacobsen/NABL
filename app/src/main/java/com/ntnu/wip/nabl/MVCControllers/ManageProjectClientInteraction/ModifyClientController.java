package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntnu.wip.nabl.MVCView.ModifyClient.ModifyClientView;

public class ModifyClientController extends Fragment {
    ModifyClientView mvcView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new ModifyClientView(inflater, null);

        return mvcView.getRootView();
    }
}
