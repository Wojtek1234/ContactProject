package pl.wmaciejewski.contactproject.modelView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by w.maciejewski on 2014-11-03.
 */
public class BitmapByteChanger {


    public byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap ByteArrayToBitmap(byte [] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
