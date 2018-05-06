package com.ntnu.wip.nabl.MVCControllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ntnu.wip.nabl.Adapters.CompanyAdapter;
import com.ntnu.wip.nabl.Adapters.CompanyListAdapter;
import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.MVCView.SummaryView.SummaryView;
import com.ntnu.wip.nabl.Models.Company;
import com.ntnu.wip.nabl.Models.CompanyContainer;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.Observers.Observers.ObserverFactory;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

import java.util.ArrayList;
import java.util.List;

public class Summary extends AppCompatActivity {

    private SummaryView mvcView;
    private List<Company> companies;

    FireStoreClient client;
    FirestoreAuthentication auth;
    CompanyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = new SummaryView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.summaryTitle));
        setContentView(mvcView.getRootView());
        initialise();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void initialise() {
        client = new FireStoreClient(this);
        auth = new FirestoreAuthentication();
        fetchCompanies();
    }

    private void fetchCompanies() {
        String correlationId = Utils.generateUniqueId(20);
        Observer observer = ObserverFactory.create(ObserverFactory.FETCH_COMPANIES_CLIENTS_PROJECTS);
        observer.setCorrelationId(correlationId);
        observer.setSubject(client);

        observer.setOnUpdateListener(companies -> {
            this.companies = (List<Company>) companies;
            fetchCompaniesData();
        });

        client.getUserCompanies(correlationId, auth.getUId());
    }

    /**
     * Fetches the most important data, and
     * creates adapters out of the data
     */
    private void fetchCompaniesData() {
        String correlationId = Utils.generateUniqueId(20);

        adapter = new CompanyAdapter(getApplicationContext(), new ArrayList<>());

        mvcView.setCompanyAdapters(adapter);
        Observer observer = ObserverFactory.create(ObserverFactory.FETCH_COMPANIES_CLIENTS_PROJECTS);
        observer.setSubject(client);
        observer.setCorrelationId(correlationId);
        observer.setOnUpdateListener(companyContainers -> {
            List<Object> objects = (List<Object>) companyContainers;

            List<CompanyContainer> companyContainersList = new ArrayList<>();

            for (Object object: objects) {
                companyContainersList.add((CompanyContainer) object);
            }

            this.adapter.addData(companyContainersList);

        });

        client.getLogEntriesByCompany(correlationId, companies);
    }
}
