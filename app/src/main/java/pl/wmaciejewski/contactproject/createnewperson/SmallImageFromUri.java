package pl.wmaciejewski.contactproject.createnewperson;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import pl.wmaciejewski.contactproject.R;

/**
 * Created by Wojtek on 2014-11-02.
 */
public class SmallImageFromUri {
    private final HashMap<Double,Integer> sizes;
 /*   36x36 (0.75x) for low-density
    48x48 (1.0x baseline) for medium-density
    72x72 (1.5x) for high-density
    96x96 (2.0x) for extra-high-density
    180x180 (3.0x) for extra-extra-high-density
    192x192 (4.0x) for extra-extra-extra-high-density (launcher icon only; see note above)*/
    private final ContentResolver contentResolver;
    private final float scale;

    public SmallImageFromUri(ContentResolver contentResolver,float density){
        this.contentResolver=contentResolver;
        this.scale=density;
        sizes=new HashMap<Double, Integer>();
        sizes.put(0.75,36);
        sizes.put(1.0,48);
        sizes.put(1.5,72);
        sizes.put(2.0,96);
        sizes.put(3.0,180);
        sizes.put(4.0,192);
    }

    public byte[] getScaledBitmap(Uri imageUri){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, sizes.get( new Double(scale)), sizes.get( new Double(scale))), Matrix.ScaleToFit.CENTER);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true).compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

}
