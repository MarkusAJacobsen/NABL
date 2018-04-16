package com.ntnu.wip.nabl.MVCView.OverviewClient;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IOverviewClient extends IAbstractMvcView {
    interface ButtonListener {
        void modifyPressed();
        void deletePressed();
    }
}
