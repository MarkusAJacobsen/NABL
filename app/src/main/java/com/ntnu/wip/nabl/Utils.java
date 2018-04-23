package com.ntnu.wip.nabl;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Utils {
    public static void askPermissionFragment(Fragment frag, String[] permission) {
        frag.requestPermissions(permission, 0);
    }

    public static String getHumanReadableDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }
}
