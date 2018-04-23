package com.ntnu.wip.nabl.MVCView.ClientInput;

import android.graphics.drawable.Drawable;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IClientInputView extends IAbstractMvcView {
    interface ClientInputListener {
        void getWarningDrawable();
    }

    void registerListener(ClientInputListener listener);
    void setName(String name);
    void setPhone(int phone);
    void setEmail(String email);
    void setStreet(String street);
    void setHouseNumber(String number);
    void setZipCode(int zipcode);
    void setCity(String city);
    String getName();
    String getPhone();
    String getEmail();
    String getStreet();
    String getHouseNumber();
    String getZipcode();
    String getCity();
    boolean checkValidity();
    void setWarningColor(int color);
    void setEditWarning(Drawable editWarningIcon);
}
