package com.ntnu.wip.nabl;

import android.support.v4.app.Fragment;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Utils class contain global static functions which may be used by every class
 */
public final class Utils {

    /**
     * Fragment ask for permissions routine
     * @param frag Fragment
     * @param permission String[]
     */
    public static void askPermissionFragment(Fragment frag, String[] permission) {
        frag.requestPermissions(permission, 0);
    }

    /**
     * Convert an date to a human readable string with format dd.MM.yyyy
     * @param date Date
     * @return String
     */
    public static String getHumanReadableDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * Decode the elements of an date String, where each elements is separated by "."
     * @param dateString String
     * @return List<Integer>
     */
    public static List<Integer> decodeDateString(String dateString) {
        String[] split = dateString.split("[\\.]");
        List<Integer> toReturn = new ArrayList<>();

        for(String part : split) {
            toReturn.add(Integer.parseInt(part));
        }

        return toReturn;
    }

    /**
     * Generate an unique id using letters and numbers.
     * Made by {@link org.apache.commons.lang3}
     * @param length int
     * @return String
     */
    public static String generateUniqueId(int length){
        return RandomStringUtils.random(length, true, true);
    }
}