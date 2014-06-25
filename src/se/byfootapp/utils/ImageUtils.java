package se.byfootapp.utils;

import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public abstract class ImageUtils {

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(CompressFormat.PNG, 100, buffer);
        return buffer.toByteArray();
    }
}
