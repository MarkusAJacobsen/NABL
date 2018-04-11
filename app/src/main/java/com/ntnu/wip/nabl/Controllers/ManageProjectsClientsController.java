package com.ntnu.wip.nabl.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ntnu.wip.nabl.MVCView.ManageProjectClient.IManageProjectClientView;
import com.ntnu.wip.nabl.MVCView.ManageProjectClient.ManageProjectClientView;
import com.ntnu.wip.nabl.R;

/**
 * Created by markusja on 4/11/18.
 */

public class ManageProjectsClientsController extends AppCompatActivity implements
                                        IManageProjectClientView.ResourceViewerListener,
                                        IManageProjectClientView.ResourceSelectorListener {
    private ManageProjectClientView mvcView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mvcView = new ManageProjectClientView(getLayoutInflater(), null);
        mvcView.registerResourceSelectorListener(this);
        mvcView.registerResourceViewerListener(this);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.manageProjectsClientsTitle));
        fetchResourceSelectorItems();


        setContentView(mvcView.getRootView());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void resourceSelected() {
        Toast.makeText(getApplicationContext(), "Selector selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSpinnerResourceSelected(int position) {
        switch(position) {
            case 0: /*Projects, fetch projects and update ResourceViewer*/ break;
            case 1: /*Clients, fetch clients and update ResourceViewer*/
        }
        Toast.makeText(getApplicationContext(), "Resource selected "+position, Toast.LENGTH_SHORT).show();
    }

    private void fetchResourceSelectorItems(){
        final String[] resourceSelectorItems = getResources().getStringArray(R.array.manageProjectAndClientsSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, resourceSelectorItems);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mvcView.setResourceSelectorAdapter(adapter);
    }
}
