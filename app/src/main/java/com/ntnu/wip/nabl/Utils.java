package com.ntnu.wip.nabl;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class Utils {
    public static void askPermissionFragment(Fragment frag, String[] permission) {
        frag.requestPermissions(permission, 0);
    }

    public static String getHumanReadableDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static List<Integer> decodeDateString(String dateString) {
        String[] split = dateString.split("[\\.]");
        List<Integer> toReturn = new ArrayList<>();

        for(String part : split) {
            toReturn.add(Integer.parseInt(part));
        }

        return toReturn;
    }

    public static String generateUniqueId(int length){
        return RandomStringUtils.random(length, true, true);
    }
}
