package com.ntnu.wip.nabl.MVCView.ClientInput;

import android.graphics.drawable.Drawable;

import com.ntnu.wip.nabl.MVCView.IAbstractMvcView;

/**
 * Interface for Client input View.
 */
public interface IClientInputView extends IAbstractMvcView {
    /**
     * Methods which this can invoke in Listeners
     */
    interface ClientInputListener {

        /**
         * Get warning drawable
         */
        void getWarningDrawable();
    }

    /**
     * Register a listener
     * Will enable an implementing class to invoke
     * functions in the listener.
     * @param listener ClientInputListener
     */
    void registerListener(ClientInputListener listener);

    /**
     * Set text in field 'Name'
     * @param name String
     */
    void setName(String name);

    /**
     * Set text in field 'Phone
     * @param phone int
     */
    void setPhone(int phone);

    /**
     * Set text in field 'Email'
     * @param email String
     */
    void setEmail(String email);

    /**
     * Set text in field 'Street'
     * @param street String
     */
    void setStreet(String street);

    /**
     * Set text in field 'Street number'
     * @param number String
     */
    void setHouseNumber(String number);

    /**
     * Set text in field 'ZipCode'
     * @param zipcode int
     */
    void setZipCode(int zipcode);

    /**
     * Set text in field 'City'
     * @param city String
     */
    void setCity(String city);

    /**
     * Get text from field 'Name'
     * @return String
     */
    String getName();

    /**
     * Get text from field 'Phone'
     * @return String
     */
    String getPhone();

    /**
     * Get text from field 'Email'
     * @return String
     */
    String getEmail();

    /**
     * Get text from field 'Street'
     * @return String
     */
    String getStreet();

    /**
     * Get text from field 'Street number'
     * @return String
     */
    String getHouseNumber();

    /**
     * Get text from field 'ZipCode'
     * @return String
     */
    String getZipcode();

    /**
     * Get text from field 'City'
     * @return String
     */
    String getCity();

    /**
     * Check if user inputted fields are valid
     * @return boolean - true if okey, false if missing or wrong
     */
    boolean checkValidity();

    /**
     * Set input field warning icon color
     * @param color int
     */
    void setWarningColor(int color);

    /**
     * Set input field warning icon
     * @param editWarningIcon Drawable
     */
    void setEditWarning(Drawable editWarningIcon);
}
