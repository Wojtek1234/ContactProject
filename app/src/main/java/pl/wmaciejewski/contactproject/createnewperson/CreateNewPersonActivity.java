package pl.wmaciejewski.contactproject.createnewperson;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import pl.wmaciejewski.contactproject.R;
import pl.wmaciejewski.contactproject.createnewperson.modelHolder.PersonDataHolder;
import pl.wmaciejewski.contactproject.database.entitys.Person;


public class CreateNewPersonActivity extends Activity {
    private static boolean NEW_INTENT_FLAG=true;

    private PersonDataHolder personDataHolder=PersonDataHolder.getInstance().getInstance();
    private ImageView imageView;
    private EditText nameEdit,surnameEdit,mailEdit,phoneEdit;
    private Uri imageUri;
    private Bitmap smallImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_person);
        Intent intent=getIntent();

        String aaa=intent.getExtras().getString("dupa");
        if(NEW_INTENT_FLAG){

            personDataHolder.clearPersonFileds();
            NEW_INTENT_FLAG=false;

        }else{
            Log.d("druga","dupa");
        }




    }


    private void initGUI(){
        initializeComponents();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

    }

    private void initializeComponents() {
        nameEdit=(EditText)findViewById(R.id.editNameView);
        surnameEdit=(EditText)findViewById(R.id.editSurnameView);
        mailEdit=(EditText)findViewById(R.id.editEmailView);
        phoneEdit=(EditText)findViewById(R.id.phoneEditView);
        imageView=(ImageView)findViewById(R.id.imagePersonView);
    }

    private void getImage() {
        //TODO wejscie Dialog z katalogu/z Kamery jak z katalogu to wybor pliku jak z kamery to Intencja, zrobic Dialog
    }

    private void initGUI(Person person){

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



}
