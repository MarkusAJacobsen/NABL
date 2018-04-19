package com.ntnu.wip.nabl.MVCView.Register;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IRegisterView extends IAbstractMvcView {
    interface ToggleListener {
        void projectPressed();
        void clientPressed();
    }

    void registerListener(ToggleListener listener);
    void setProjectText(String text);
    void setClientText(String text);
}
