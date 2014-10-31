package pl.wmaciejewski.contactproject.createnewperson;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import pl.wmaciejewski.contactproject.MainActivity;
import pl.wmaciejewski.contactproject.R;
import pl.wmaciejewski.contactproject.createnewperson.dialogs.WrongValueDialog;
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
    private Button saveButton;
    private Uri imageUri;
    private Bitmap smallImage;
    private HashMap<EditText,Validator> validators;
    private boolean areWeGoing=false;

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
            NEW_INTENT_FLAG=false;


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
        setUpComponents();
        setSaveButtonListener();
        setUpValidators();
    }

    private void setUpValidators() {
        validators=new HashMap<EditText, Validator>();
        validators.put(mailEdit,new EmailValidator());
        validators.put(phoneEdit,new PhoneNumberValidator());
    }



    private void setSaveButtonListener() {
        saveButton.setOnClickListener(new saveButListener());
    }
    private void setUpComponents() {
        nameEdit = (EditText) findViewById(R.id.editNameView);
        surnameEdit = (EditText) findViewById(R.id.editSurnameView);
        mailEdit = (EditText) findViewById(R.id.editEmailView);
        phoneEdit = (EditText) findViewById(R.id.phoneEditView);
        imageView = (ImageView) findViewById(R.id.imagePersonView);
        saveButton=(Button)findViewById(R.id.save_button);
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

    private void createWrongDialog(String text) {
        final WrongValueDialog wrongValueDialog=new WrongValueDialog(CreateNewPersonActivity.this,text);
        wrongValueDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                areWeGoing=wrongValueDialog.isResult();
                if(areWeGoing) sendResultIntent();
            }
        });
        wrongValueDialog.show();
    }


class saveButListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
            int x=0;
            x=doOnEmail();
            x=x+doOnPhone();
            switch (x) {
                case 1:
                    createWrongDialog(getResources().getString(R.string.wrong_email));
                    break;
                case 2:
                    createWrongDialog(getResources().getString(R.string.wrong_phone));
                    break;
                case 3:
                    createWrongDialog(getResources().getString(R.string.wrong_both));
                    break;
                default:
                    sendResultIntent();
                    break;
            }




        }




    private int doOnEmail() {
        if(!validateInput(mailEdit)) return 1;
        else return 0;
    }

    private int doOnPhone() {
        if(!validateInput(phoneEdit)) return 2;
        else return 0;
    }
}

    private void sendResultIntent() {
        personDataHolder.getPerson().setImage(imageUri);
        personDataHolder.getPerson().setName(nameEdit.getText().toString());
        personDataHolder.getPerson().setSurname(surnameEdit.getText().toString());
        personDataHolder.getPerson().setEmail(mailEdit.getText().toString());
        personDataHolder.getPerson().setPhoneNumber(phoneEdit.getText().toString());
        ParcelPerson parcelPerson=new ParcelPerson(personDataHolder.getPerson());
        Intent resultIntent=new Intent();
        resultIntent.putExtra(MainActivity.REQUEST_CREATE_PERSON,parcelPerson);
        NEW_INTENT_FLAG=true;
        finish();
    }


}
