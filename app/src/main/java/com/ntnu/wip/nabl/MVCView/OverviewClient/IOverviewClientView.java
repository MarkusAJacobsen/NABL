package com.ntnu.wip.nabl.MVCView.OverviewClient;


import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

public interface IOverviewClientView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface ButtonListener {
        /**
         * Call button pressed
         */
        void callPressed();

        /**
         * Message button pressed
         */
        void messagePressed();

        /**
         * Email button pressed
         */
        void emailPressed();
    }

    /**
     * Register listener
     * @param listener ButtonListener
     */
    void registerListener(ButtonListener listener);

    /**
     * Set text in 'Name' field
     * @param name String
     */
    void setName(String name);

    /**
     * Set text in 'Email' field
     * @param email String
     */
    void setEmail(String email);

    /**
     * Set text in 'Phone' field
     * @param phone String
     */
    void setPhone(String phone);

    /**
     * Set text in 'Address' field
     * @param address String
     */
    void setAddress(String address);
}
