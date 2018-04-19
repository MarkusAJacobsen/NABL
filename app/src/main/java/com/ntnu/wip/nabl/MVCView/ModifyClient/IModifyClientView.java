package com.ntnu.wip.nabl.MVCView.ModifyClient;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IModifyClientView extends IAbstractMvcView {
    interface ModifyClientListener {
        void savePressed();
    }

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
}
