package com.ntnu.wip.nabl.MVCView.OverviewClient;

import android.view.View;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IOverviewClientView extends IAbstractMvcView {
    interface ButtonListener {
        void modifyPressed();
        void deletePressed();
        void callPressed();
        void messagePressed();
        void emailPressed();
    }

    void registerListener(ButtonListener listener);
    void setName(String name);
    void setEmail(String email);
    void setPhone(String phone);
    void setAddress(String address);
}
