package com.lblzr.cookbookplus.helpers;

import android.os.Environment;

public class FileHelper {

    public static String getFullPath(String filePath) {
        return "/storage/emulated/0/Download/" + filePath; //hqdefault (1).jpg";
        //return Environment.getStorageDirectory() + filePath;
    }
}
