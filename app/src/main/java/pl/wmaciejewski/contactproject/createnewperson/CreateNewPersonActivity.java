package pl.wmaciejewski.contactproject.createnewperson;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import pl.wmaciejewski.contactproject.R;
import pl.wmaciejewski.contactproject.createnewperson.modelHolder.PersonDataHolder;
import pl.wmaciejewski.contactproject.createnewperson.validators.EmailValidator;
import pl.wmaciejewski.contactproject.createnewperson.validators.PhoneNumberValidator;
import pl.wmaciejewski.contactproject.createnewperson.validators.Validator;
import pl.wmaciejewski.contactproject.database.entitys.Person;
import pl.wmaciejewski.contactproject.modelView.ParcelPerson;


public class CreateNewPersonActivity extends Activity {
    private static boolean NEW_INTENT_FLAG = true;

    private PersonDataHolder personDataHolder = PersonDataHolder.getInstance().getInstance();
    private ImageView imageView;
    private EditText nameEdit, surnameEdit, mailEdit, phoneEdit;
    private Uri imageUri;
    private Bitmap smallImage;
    private HashMap<EditText,Validator> validators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_person);
        Intent intent = getIntent();

        ParcelPerson parcelable = intent.getExtras().getParcelable("person");
        if (NEW_INTENT_FLAG) {
            if (parcelable != null) {
                initGUI(parcelable.getPerson());
            } else {
                doOnCreateIntent();
            }


        } else {
            initGUI(parcelable.getPerson());
        }


    }

    private void doOnCreateIntent() {
        personDataHolder.clearPersonFileds();
        NEW_INTENT_FLAG = false;
        initGUI();
    }


    private void initGUI() {
        initializeComponents();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

    }

    private void initializeComponents() {
        nameEdit = (EditText) findViewById(R.id.editNameView);
        surnameEdit = (EditText) findViewById(R.id.editSurnameView);
        mailEdit = (EditText) findViewById(R.id.editEmailView);
        phoneEdit = (EditText) findViewById(R.id.phoneEditView);
        imageView = (ImageView) findViewById(R.id.imagePersonView);
        validators.put(mailEdit,new EmailValidator());
        validators.put(phoneEdit,new PhoneNumberValidator());
    }

    private void getImage() {
        //TODO wejscie Dialog z katalogu/z Kamery jak z katalogu to wybor pliku jak z kamery to Intencja, zrobic Dialog
    }

    private boolean validateInput(EditText editText){

        return validators.get(editText).validate(editText.getText());
    }

    private void initGUI(Person person) {
        initializeComponents();
        nameEdit.setText(person.getName());
        surnameEdit.setText(person.getSurname());
        mailEdit.setText(person.getEmail());
        phoneEdit.setText(person.getPhoneNumber());
        setBitmap(person.getImage());


    }

    private void setBitmap(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.no_photo));
        } catch (IOException e) {
            e.printStackTrace();
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.no_photo));
        }
    }




}
