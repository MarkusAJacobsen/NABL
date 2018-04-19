package com.ntnu.wip.nabl.MVCView.OverviewClient;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewClientView implements IOverviewClientView {
    private View rootView;
    private ButtonListener listener;

    @BindView(R.id.modify) Button modify;
    @BindView(R.id.delete) Button delete;
    @BindView(R.id.name) TextView mName;
    @BindView(R.id.address) TextView mAddress;
    @BindView(R.id.phone) TextView mPhone;
    @BindView(R.id.email) TextView mEmail;
    @BindView(R.id.callAction) ImageView mCallButton;
    @BindView(R.id.messageAction) ImageView mMessageButton;
    @BindView(R.id.emailAction) ImageView mEmailButton;

    public OverviewClientView(LayoutInflater inflater, ViewGroup container){
        rootView = inflater.inflate(R.layout.manage_client, container);
        ButterKnife.bind(this, rootView);

        activateModifyButton();
        activateDeleteButton();
        activateCallButton();
        activateMessageButton();
        activateEmailButton();
    }

    private void activateModifyButton(){
        modify.setOnClickListener(view -> {
           if(listener != null) {
               listener.modifyPressed();
           }
        });
    }

    private void activateDeleteButton(){
        delete.setOnClickListener(view -> {
            if(listener != null) {
                listener.deletePressed();
            }
        });
    }

    private void activateCallButton(){
        mCallButton.setOnClickListener(view -> {
            if(listener != null) {
                listener.callPressed();
            }
        });
    }

    private void activateMessageButton(){
        mMessageButton.setOnClickListener(view -> {
            if(listener != null) {
                listener.messagePressed();
            }
        });
    }

    private void activateEmailButton(){
        mEmailButton.setOnClickListener(view -> {
            if(listener != null) {
                listener.emailPressed();
            }
        });
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {

    }

    @Override
    public void setActionBarTitle(String title) {

    }

    @Override
    public void registerListener(ButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public void setName(String name) {
        mName.setText(name);
    }

    @Override
    public void setEmail(String email) {
        mEmail.setText(email);
    }

    @Override
    public void setPhone(String phone) {
        mPhone.setText(phone);
    }

    @Override
    public void setAddress(String address) {
        mAddress.setText(address);
    }
}
