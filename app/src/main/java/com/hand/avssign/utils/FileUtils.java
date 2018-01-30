package com.hand.avssign.utils;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    public static Boolean externalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File picturesDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    public static String picturesDirStr() {
        return picturesDir().toString();
    }


    public static String strSDCardUnavailable() {
        return "SD-карта не доступна: " + Environment.getExternalStorageState();
    }
}
