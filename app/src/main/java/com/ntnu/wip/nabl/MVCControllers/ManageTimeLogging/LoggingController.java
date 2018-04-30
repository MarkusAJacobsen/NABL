package com.ntnu.wip.nabl.MVCControllers.ManageTimeLogging;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ntnu.wip.nabl.MVCControllers.IChangeScreen;
import com.ntnu.wip.nabl.MVCView.LoggingView.ILoggingView;
import com.ntnu.wip.nabl.MVCView.LoggingView.LoggingView;
import com.ntnu.wip.nabl.R;

/**
 * Class that control Time logging activity.
 * Created by a7med on 29.04.18.
 */
public class LoggingController extends AppCompatActivity implements ILoggingView.LoggingViewListener,
        IChangeScreen.Fragment {
    private LoggingView mvcView;

    /**
     * Android Activity Life Cycle function. Runs when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mvcView = new LoggingView(getLayoutInflater(), null);
        this.mvcView.registerLoggingListener(this);

        this.mvcView.setActionBar(getSupportActionBar());
        this.mvcView.setActionBarTitle(getString(R.string.loggingView));

        configureButtons();

        setContentView(this.mvcView.getRootView());
    }

    /**
     * Function to add text on View's Toggle-buttons.
     */
    private void configureButtons() {
        this.mvcView.setProjectText(getString(R.string.project));
        this.mvcView.setClientText(getString(R.string.client));
    }

    /**
     * Project button is pressed.
     * Show Project List Fragment
     */
    @Override
    public void projectPressed() {
        //TODO => Show under a list of projects to choose between them
        try {
            transactionManager(LogAProjectController.class, null);
            this.mvcView.updateTextViewTitle(getString(R.string.projectList));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Client button is pressed.
     * Show Client List Fragment
     */
    @Override
    public void clientPressed() {
        //TODO => Show under a list of clients to choose between them
        try {
            transactionManager(LogAClientController.class, null);
            this.mvcView.updateTextViewTitle(getString(R.string.projectList));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to apply the transaction and show the fragment.
     * @param frag
     * @param args
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    public void transactionManager(Class<? extends Fragment> frag, Bundle args)
            throws IllegalAccessException, InstantiationException {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment listFragment;

        listFragment = frag.newInstance();
        if(args != null) {
            listFragment.setArguments(args);
        }

        transaction.replace(R.id.listView, listFragment);
        transaction.commit();
    }
}
