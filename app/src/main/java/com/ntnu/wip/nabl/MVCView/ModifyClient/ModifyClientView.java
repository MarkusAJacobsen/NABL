package com.ntnu.wip.nabl.MVCView.ModifyClient;

import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyClientView implements IModifyClientView {
    private View rootView;
    private ActionBar actionBar;
    @BindView(R.id.modifyName) TextView mName;
    @BindView(R.id.modifyPhone) TextView mPhone;
    @BindView(R.id.modifyEmail) TextView mEmail;
    @BindView(R.id.modifyStreet) TextView mStreet;
    @BindView(R.id.modifyNumber) TextView mHouseNumber;
    @BindView(R.id.modifyZipcode) TextView mZipcode;
    @BindView(R.id.modifyCity) TextView mCity;

    public ModifyClientView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.modify_client, container);
        ButterKnife.bind(this, rootView);


    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void setActionBar(ActionBar actionbar) {
        this.actionBar = actionbar;
    }

    @Override
    public void setActionBarTitle(String title) {

    }

    @Override
    public void setName(String name) {
        mName.setText(name);
    }

    @Override
    public void setPhone(int phone) {
        mPhone.setText(String.valueOf(phone));
    }

    @Override
    public void setEmail(String email) {
        mEmail.setText(email);
    }

    @Override
    public void setStreet(String street) {
        mStreet.setText(street);
    }

    @Override
    public void setHouseNumber(String number) {
        mHouseNumber.setText(number);
    }

    @Override
    public void setZipCode(int zipcode) {
        mZipcode.setText(String.valueOf(zipcode));
    }

    @Override
    public void setCity(String city) {
        mCity.setText(city);
    }

    //
    // End Interface implementation
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    //
    // End Privates
    //---------------------------------------------------------------------------------------------

}
