package com.lblzr.cookbookplus.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import com.lblzr.cookbookplus.R;

import java.io.File;

public class FileHelper {

    public static String getFullPath(String filePath) {
        return "/storage/emulated/0/Download/" + filePath; //hqdefault (1).jpg";
        //return Environment.getStorageDirectory() + filePath;
    }

    public static Bitmap getBitmap(String imagePath) {
        File imgFile = new File(getFullPath(imagePath));

        if (imgFile.exists()) {
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }

        return null;
    }
}
