package pl.wmaciejewski.contactproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;

import pl.wmaciejewski.contactproject.database.entitys.Person;


public class CreateNewPersonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_person);


        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.no_photo);

       Person person=new Person("D",null,null,null,null,setPhpotoAsByteArray(icon),null);


        Intent resultIntent=new Intent();


        resultIntent.putExtra("Person",person);
        setResult(MainActivity.REQUEST_CREATE_PERSON,resultIntent);

        finish();

    }

    private byte[] setPhpotoAsByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }



}
