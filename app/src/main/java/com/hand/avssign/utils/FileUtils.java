package com.hand.avssign.utils;

import android.os.Environment;

public class FileUtils {

    public static Boolean externalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String strSDCardUnavailable() {
        return "SD-карта не доступна: " + Environment.getExternalStorageState();
    }
}
