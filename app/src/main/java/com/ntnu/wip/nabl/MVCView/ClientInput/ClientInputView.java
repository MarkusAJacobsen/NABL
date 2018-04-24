package com.ntnu.wip.nabl.MVCView.ClientInput;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntnu.wip.nabl.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientInputView implements IClientInputView {
    private View rootView;
    private ActionBar actionBar;
    private ClientInputListener listener;
    private int warningColor = Color.RED;
    private Drawable editWarning;
    private List<TextView> textViews;

    @BindView(R.id.modifyName) TextView mName;
    @BindView(R.id.modifyPhone) TextView mPhone;
    @BindView(R.id.modifyEmail) TextView mEmail;
    @BindView(R.id.modifyStreet) TextView mStreet;
    @BindView(R.id.modifyNumber) TextView mHouseNumber;
    @BindView(R.id.modifyZipcode) TextView mZipcode;
    @BindView(R.id.modifyCity) TextView mCity;

    public ClientInputView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.client_input, container);
        ButterKnife.bind(this, rootView);
        configureTextViewList();
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
    public void registerListener(ClientInputListener listener) {
        this.listener = listener;
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

    @Override
    public String getName() {
        return mName.getText().toString();
    }

    @Override
    public String getPhone() {
        return mPhone.getText().toString();
    }

    @Override
    public String getEmail() {
        return mEmail.getText().toString();
    }

    @Override
    public String getStreet() {
        return mStreet.getText().toString();
    }

    @Override
    public String getHouseNumber() {
        return mHouseNumber.getText().toString();
    }

    @Override
    public String getZipcode() {
        return mZipcode.getText().toString();
    }

    @Override
    public String getCity() {
        return mCity.getText().toString();
    }

    @Override
    public boolean checkValidity() {
        boolean completeResult = true;

        for(TextView view : textViews) {
            if(view.getText().toString().trim().equals("")) {
                setWarningDrawable(view);
                completeResult = false;
            } else {
                tryToRemoveDrawable(view);
            }
        }

        return completeResult;
    }

    @Override
    public void setWarningColor(int color) {
        this.warningColor = color;
    }

    @Override
    public void setEditWarning(Drawable editWarningIcon) {
        editWarning = editWarningIcon;
    }

    //
    // End Interface implementation
    //---------------------------------------------------------------------------------------------
    // Privates
    //

    private void setWarningDrawable(TextView view){
        getWarningEditIcon();

        if(editWarning == null) {
            return;
        }

        editWarning.setBounds(0, 0, editWarning.getIntrinsicWidth(), editWarning.getIntrinsicHeight());
        editWarning.setColorFilter(warningColor, PorterDuff.Mode.SRC_ATOP);
        view.setCompoundDrawables(null, null, editWarning, null);
    }

    private void getWarningEditIcon(){
        if(editWarning == null && listener != null) {
            listener.getWarningDrawable();
        }
    }

    private void configureTextViewList(){
        textViews = new ArrayList<>();
        textViews.add(mName);
        textViews.add(mPhone);
        textViews.add(mEmail);
        textViews.add(mStreet);
        textViews.add(mHouseNumber);
        textViews.add(mCity);
        textViews.add(mZipcode);
    }

    private void tryToRemoveDrawable(TextView view) {
        if(view.getCompoundDrawables() != null) {
            view.setCompoundDrawables(null, null, null, null);
        }
    }

    //
    // End privates
    //---------------------------------------------------------------------------------------------
}
