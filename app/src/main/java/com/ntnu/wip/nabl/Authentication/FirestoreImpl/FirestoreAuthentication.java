package com.ntnu.wip.nabl.Authentication.FirestoreImpl;

import android.content.Context;
import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ntnu.wip.nabl.Authentication.IAuthentication;
import com.ntnu.wip.nabl.Observers.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Firestore implementation of authentication handling
 */
public class FirestoreAuthentication implements IAuthentication {
    //Result code for login intent
    private static final int RC_SIGN_IN = 99;
    private List<Observer> observers = new ArrayList<>();


    // Authentication providers
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );


    /**
     * Create a firesbase login intent
     * @return Intent
     */
    @Override
    public Intent signIn() {
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
    }

    /**
     * Logout currently logged in user
     * @param context Context
     */
    @Override
    public void signOut(Context context) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener(task -> {
                    notifyObservers();
                });
    }

    /**
     * Get current logged in user
     * @return FirebaseUser
     */
    @Override
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Get intent result code
     * @return Int
     */
    @Override
    public int getResultCode() {
        return RC_SIGN_IN;
    }

    /**
     * Get currently logged in username
     * @return String
     */
    @Override
    public String getFullName() {
        FirebaseUser user = getCurrentUser();

        if(user != null) {
            return user.getDisplayName();
        }
        return null;
    }

    /**
     * Get currently logged in user email
     * @return String
     */
    @Override
    public String getEmail() {
        FirebaseUser user = getCurrentUser();

        if(user != null) {
            return user.getEmail();
        }
        return null;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(){
        for(Observer observer : observers) {
            observer.update();
        }
    }
}
