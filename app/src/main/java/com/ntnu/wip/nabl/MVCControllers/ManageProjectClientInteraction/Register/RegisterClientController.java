package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntnu.wip.nabl.MVCView.RegisterClient.RegisterClientView;

public class RegisterClientController extends Fragment {
    private RegisterClientView mvcView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new RegisterClientView(inflater, null);


        return mvcView.getRootView();
    }
}
