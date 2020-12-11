package com.lblzr.cookbookplus.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.lblzr.cookbookplus.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.UUID;

public class FileHelper {

    private static class ExtensionFilter implements FilenameFilter {

        private String ext;

        public ExtensionFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }

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
                    new OutputStreamWriter(new FileOutputStream(getFullPath(context, file)));
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

    public static Reader getReader(Context context, String file) throws FileNotFoundException {
        return getReader(getFile(context, file));
    }

    public static Reader getReader(File file) throws FileNotFoundException {
        return new BufferedReader(new FileReader(file));
    }

    public static File[] listFiles(Context context, String ext) {
        File storageDir = context.getExternalFilesDir("recipes");
        if(storageDir != null) {
            return storageDir.listFiles(new ExtensionFilter(ext));
        }
        return new File[0];
    }

    public static void deleteFiles(Context context, String ext)
    {
        for (File file : listFiles(context, ext))
        {
            if (!file.isDirectory())
            {
                if(file.delete())
                    Log.d("CBP", "Deleted: " + file.getAbsolutePath());
            }
        }
    }

    public static String getBase64FromBitmap(File imageFile) {
        Bitmap bm = getBitmap(imageFile);
        return getBase64FromBitmap(bm);
    }

    public static String getBase64FromBitmap(Bitmap bm) {
        if(bm != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] a = baos.toByteArray();
            return Base64.encodeToString(a, Base64.DEFAULT);
        }
        return "";
    }

    public static Bitmap getBitmapFromBase64(String base64) {
        byte[] imageBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
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
