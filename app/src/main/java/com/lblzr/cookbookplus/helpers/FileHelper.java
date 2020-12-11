package com.lblzr.cookbookplus.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.lblzr.cookbookplus.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

public class FileHelper {

    public static String getFullPath(Context context, String file) {
        File storageDir = context.getExternalFilesDir("recipes");
        return new File(storageDir, file).getAbsolutePath();
    }

    public static File getFile(Context context, String file) {
        File storageDir = context.getExternalFilesDir("recipes");
        return new File(storageDir, file);
    }

    public static void writeString(Context context, String file, String string) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(context.openFileOutput(getFullPath(context, file), Context.MODE_PRIVATE));
            outputStreamWriter.write(string);
            outputStreamWriter.close();
        }
        catch (IOException ex) {
            Log.e("CBP", "Failed to write file: " + ex.toString());
        }
    }

    public static String readString(Context context, String file) {
        StringBuilder sb = new StringBuilder();

        try {
            String line;
            BufferedReader in = new BufferedReader(new FileReader(getFile(context, file)));
            while ((line = in.readLine()) != null) sb.append(line);
        }
        catch (IOException ex) {
            Log.e("CBP", "Failed to read file: " + ex.toString());
        }

        return sb.toString();
    }

    public static Bitmap getBitmap(Context context, String imageFile) {
        File storageDir = context.getExternalFilesDir("recipes");

        if(storageDir != null){
            Log.d("CBP", imageFile);
            File imgFile = new File(storageDir.getAbsolutePath(), imageFile);

            if (imgFile.exists()) {
                return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
        }

        return null;
    }

    public static Bitmap getBitmap(File imageFile) {
        if (imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }
        return null;
    }

    public static File createImageFile(Context context) throws IOException {
        String imageFileName = "cbp_" + UUID.randomUUID().toString();
        File storageDir = context.getExternalFilesDir("recipes");
        storageDir.mkdirs();
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        Log.d("CBP","Created image file at " + image.getAbsolutePath());

        return image;
    }
}
