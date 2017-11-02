package com.idirin.catfactstask.util;

import android.os.Build;

public final class TestUtil {


    public static boolean isRoboUnitTest() {
        return "robolectric".equals(Build.FINGERPRINT);
    }

    public static synchronized boolean isRunningEspressoTest() {

        boolean isTest;

        try {
            Class.forName("android.support.test.espresso.Espresso");
            isTest = true;
        } catch (ClassNotFoundException e) {
            isTest = false;
        }

        return isTest;
    }


}