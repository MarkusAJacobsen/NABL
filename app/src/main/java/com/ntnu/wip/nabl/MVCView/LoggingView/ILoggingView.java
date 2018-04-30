package com.ntnu.wip.nabl.MVCView.LoggingView;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface connect between LoggingView (View) and it's (controller) LoggingController
 * Created by a7med on 29.04.18.
 */
public interface ILoggingView extends IAbstractMvcView {
    // Functions to implement in the controller
    interface LoggingViewListener {
        void projectPressed();
        void clientPressed();
    }

    // Functions to implement in the View
    void registerLoggingListener(LoggingViewListener listener);
    void setProjectText(String text);
    void setClientText(String text);
    void updateTextViewTitle(String text);
}
