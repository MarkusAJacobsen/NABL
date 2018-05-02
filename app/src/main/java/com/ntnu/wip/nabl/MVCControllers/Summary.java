package com.ntnu.wip.nabl.MVCControllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ntnu.wip.nabl.Authentication.FirestoreImpl.FirestoreAuthentication;
import com.ntnu.wip.nabl.MVCView.SummaryView.SummaryView;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.Subscriptions;
import com.ntnu.wip.nabl.Observers.AddOnUpdateListener;
import com.ntnu.wip.nabl.Observers.IObserverSubject;
import com.ntnu.wip.nabl.Observers.Observer;
import com.ntnu.wip.nabl.R;

public class Summary extends AppCompatActivity {

    private SummaryView mvcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcView = new SummaryView(getLayoutInflater(), null);
        mvcView.setActionBar(getSupportActionBar());
        mvcView.setActionBarTitle(getString(R.string.summaryTitle));
        setContentView(mvcView.getRootView());
    }

    private void fetchCompaniesData() {
        FirestoreAuthentication auth = new FirestoreAuthentication();
        FireStoreClient client = new FireStoreClient(this);

        client.attach(new Observer() {
            @Override
            public void setSubject(IObserverSubject subject) {

            }

            @Override
            public void update() {

            }

            @Override
            public void update(Subscriptions sub) {
                if (sub == Subscriptions.COMPANIES) {

                }
            }

            @Override
            public void setOnUpdateListener(AddOnUpdateListener listener) {

            }
        });
    }
}
