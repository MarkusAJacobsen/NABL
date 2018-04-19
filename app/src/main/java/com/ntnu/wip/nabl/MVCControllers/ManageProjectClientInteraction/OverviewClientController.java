package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ntnu.wip.nabl.Consts.Poststamp;
import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.MVCView.OverviewClient.IOverviewClientView;
import com.ntnu.wip.nabl.MVCView.OverviewClient.OverviewClientView;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

import java.util.Locale;

public class OverviewClientController extends Fragment implements IOverviewClientView.ButtonListener,
                                                                    IChangeScreen.Fragment {
    private OverviewClientView mvcView;
    private Client model;
    private boolean modelPresent = false;

    //---------------------------------------------------------------------------------------------
    // Fragment lifecycle
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mvcView = new OverviewClientView(inflater, null);
        mvcView.registerListener(this);

        getDataFromArguments(getArguments());
        populateView();

        return mvcView.getRootView();
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //
    // End Fragment lifecycle
    // --------------------------------------------------------------------------------------------
    // Other fragment overrides
    //

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.overview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle() == getString(R.string.options)) {
            Toast.makeText(getContext(), "Menu pressed", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult){
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        call();
    }

    //
    // End other fragment overrides
    //---------------------------------------------------------------------------------------------
    // Listener Implementations
    //

    @Override
    public void modifyPressed() {
        final String parcel = new Gson().toJson(model);
        Bundle args = new Bundle();
        args.putString(Poststamp.CLIENT, parcel);


        try {
            transactionManager(ModifyClientController.class, args);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePressed() {

    }

    @Override
    public void callPressed() {
        call();
    }

    @Override
    public void messagePressed() {
        message();
    }

    @Override
    public void emailPressed() {
        email();
    }

    //
    // End Listener Implementation
    //---------------------------------------------------------------------------------------------
    // IChangeScreen Implementation
    //

    @Override
    public void transactionManager(Class <? extends android.support.v4.app.Fragment> frag, Bundle args) throws
                                                                           IllegalAccessException,
                                                                java.lang.InstantiationException {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment;

        newFragment = frag.newInstance();
        if(args != null) {
            newFragment.setArguments(args);
        }

        ft.replace(R.id.contentFrame, newFragment);
        ft.commit();
    }

    //
    // End IChangeScreen
    //---------------------------------------------------------------------------------------------
    //
    //

    private void getDataFromArguments(Bundle args){
        if(args.containsKey(Poststamp.CLIENT)) {
            final String parcel = args.getString(Poststamp.CLIENT);
            model = new Gson().fromJson(parcel, Client.class);
            modelPresent = true;
        }
    }

    private void populateView(){
        if(!modelPresent) {
            return;
        }

        mvcView.setName(model.getName());
        mvcView.setEmail(model.getContactInformation().getEmail());
        mvcView.setPhone(String.valueOf(model.getContactInformation().getPhoneNumber()));

        final String address = String.format(Locale.getDefault(), "%s %s, %s %s",
                model.getAddress().getStreet(),
                model.getAddress().getNumber(),
                model.getAddress().getZipcode(),
                model.getAddress().getCity());
        mvcView.setAddress(address);
    }

    private void call(){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+model.getContactInformation().getPhoneNumber()));
        startActivity(callIntent);
    }

    private void message(){
        Intent messageIntent = new Intent(Intent.ACTION_VIEW);
        messageIntent.setData(Uri.parse("sms:" +model.getContactInformation().getPhoneNumber()));
        startActivity(messageIntent);
    }

    private void email(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",model.getContactInformation().getEmail(), null));
        startActivity(emailIntent);
    }
}
