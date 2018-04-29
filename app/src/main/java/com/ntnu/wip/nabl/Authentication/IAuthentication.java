package com.ntnu.wip.nabl.Authentication;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;
import com.ntnu.wip.nabl.Observers.IObserverSubject;

/**
 * Authentication contract, currently slightly specified for FirebaseUI
 */
public interface IAuthentication extends IObserverSubject{
    /**
     * Get signin intent
     * @return Intent
     */
    Intent signIn();

    /**
     * Sign out current user
     * @param context Context
     */
    void signOut(Context context);

    /**
     * Get currently logged in user
     * @return Object
     */
    Object getCurrentUser();

    /**
     * Get Intent Result code
     * @return Int
     */
    int getResultCode();

    /**
     * Get currently logged in users full name
     * @return String
     */
    String getFullName();

    /**
     * Get currently logged in user email
     * @return String
     */
    String getEmail();
}
