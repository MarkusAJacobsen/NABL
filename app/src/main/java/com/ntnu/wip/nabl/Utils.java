package com.ntnu.wip.nabl;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

public final class Utils {
    public static void askPermissionFragment(Fragment frag, String[] permission) {
        frag.requestPermissions(permission, 0);
    }
}
