package pl.wmaciejewski.contactproject.createnewperson;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import pl.wmaciejewski.contactproject.MainActivity;
import pl.wmaciejewski.contactproject.R;
import pl.wmaciejewski.contactproject.createnewperson.dialogs.ImagePickerDialog;
import pl.wmaciejewski.contactproject.createnewperson.dialogs.WrongValueDialog;
import pl.wmaciejewski.contactproject.createnewperson.modelHolder.PersonDataHolder;
import pl.wmaciejewski.contactproject.createnewperson.validators.EmailValidator;
import pl.wmaciejewski.contactproject.createnewperson.validators.PhoneNumberValidator;
import pl.wmaciejewski.contactproject.createnewperson.validators.Validator;
import pl.wmaciejewski.contactproject.database.entitys.Person;
import pl.wmaciejewski.contactproject.modelView.ParcelPerson;


public class CreateNewPersonActivity extends FragmentActivity implements ImagePickerDialog.NoticeDialogListener {

    private static boolean NEW_INTENT_FLAG = true;
    private static final int SELECT_PHOTO_GALLERY = 100;
    private static final int SELECT_PHOTO_CAPTURE = 101;

    private PersonDataHolder personDataHolder = PersonDataHolder.getInstance().getInstance();
    private ImageView imageView;
    private EditText nameEdit, surnameEdit, mailEdit, phoneEdit;
    private Button saveButton;
    private Uri imageUri;
    private Bitmap smallImage;
    private HashMap<EditText, Validator> validators;
    private boolean areWeGoing = false;
    private Uri selectedImageUri;
    private CreatePhotoFile createPhotoFile;

    //TODO wyrefactorować klasy odpowiedzialne za zdjecia i inne glupoty tak zeby nie bylo tego wszystkigo tutaj
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_person);
        Intent intent = getIntent();
        try{
            ParcelPerson parcelable = intent.getExtras().getParcelable("person");
            if (NEW_INTENT_FLAG) {
                if (parcelable != null) {
                    initGUI(parcelable.getPerson());
                }
                NEW_INTENT_FLAG = false;
            } else {
                initGUI(personDataHolder.getPerson());
            }
        }catch (NullPointerException ne){
            doOnCreateIntent();
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
        validators = new HashMap<EditText, Validator>();
        validators.put(mailEdit, new EmailValidator());
        validators.put(phoneEdit, new PhoneNumberValidator());
    }

    private void setSaveButtonListener() {
        saveButton.setOnClickListener(new SaveButListener());
    }

    private void setUpComponents() {
        nameEdit = (EditText) findViewById(R.id.editNameView);
        surnameEdit = (EditText) findViewById(R.id.editSurnameView);
        mailEdit = (EditText) findViewById(R.id.editEmailView);
        phoneEdit = (EditText) findViewById(R.id.phoneEditView);
        imageView = (ImageView) findViewById(R.id.imagePersonView);
        saveButton = (Button) findViewById(R.id.save_button);
    }

    private void getImage() {
        ImagePickerDialog imagePickerDialog = new ImagePickerDialog();
        imagePickerDialog.show(getSupportFragmentManager(), getResources().getString(R.string.imagedialogString));
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO_GALLERY);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        startCamera();
    }

    public void startCamera() {
        if (checkNameField()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.put_name_first),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        File photo ;

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        photo = createPhotoFile.getFile();
        if (photo != null) startCatchCamerIntent(photo, intent);
    }

    private void startCatchCamerIntent(File photo, Intent intent) {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        selectedImageUri = Uri.fromFile(photo);
        startActivityForResult(intent, SELECT_PHOTO_CAPTURE);
    }

    private boolean checkNameField() {
        return (nameEdit.getText().toString().equals(""));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    doOnUriRecived(selectedImage);
                }
                break;
            case SELECT_PHOTO_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = selectedImageUri;
                    doOnUriRecived(selectedImage);
                }
                break;

        }
    }

    private void doOnUriRecived(Uri selectedImage) {
        personDataHolder.getPerson().setImage(selectedImage);
        setBitmap(selectedImage);
        SmallImageFromUri smallImageFromUri = new SmallImageFromUri(getContentResolver(), getResources().getDisplayMetrics().density);
        personDataHolder.getPerson().setSmallImage(smallImageFromUri.getScaledBitmap(selectedImage));
    }


    private boolean validateInput(EditText editText) {
        return validators.get(editText).validate(editText.getText());
    }

    private void initGUI(Person person) {
        initializeComponents();
        nameEdit.setText(person.getName());
        surnameEdit.setText(person.getSurname());
        mailEdit.setText(person.getEmail());
        phoneEdit.setText(person.getPhoneNumber());
        setBitmap(person.getImage());
        this.createPhotoFile = new CreatePhotoFile(this.nameEdit, getCacheDir());


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

    private void sendResultIntent() {
        setPersonData();
        ParcelPerson parcelPerson = new ParcelPerson(personDataHolder.getPerson());
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.REQUEST_CREATE_PERSON, parcelPerson);
        setResult(MainActivity.REQUEST_CREATE_PERSON_NUMBER,resultIntent);
        NEW_INTENT_FLAG = true;
        finish();
    }

    private void setPersonData() {
        personDataHolder.getPerson().setImage(imageUri);
        try {
            int  i=personDataHolder.getPerson().getSmallImage().length;
        }catch(NullPointerException ne){
            SmallImageFromUri smallImageFromUri = new SmallImageFromUri(getContentResolver(), getResources().getDisplayMetrics().density);
            personDataHolder.getPerson().setSmallImage(smallImageFromUri.getScaledBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.no_photo)));
        }
        personDataHolder.getPerson().setName(nameEdit.getText().toString());
        personDataHolder.getPerson().setSurname(surnameEdit.getText().toString());
        personDataHolder.getPerson().setEmail(mailEdit.getText().toString());
        personDataHolder.getPerson().setPhoneNumber(phoneEdit.getText().toString());
    }


    private void createWrongDialog(String text) {
        final WrongValueDialog wrongValueDialog = new WrongValueDialog(CreateNewPersonActivity.this, text);
        wrongValueDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                areWeGoing = wrongValueDialog.isResult();
                if (areWeGoing) sendResultIntent();
            }
        });
        wrongValueDialog.show();
    }


    class SaveButListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int x = 0;
            x = doOnEmail();
            x = x + doOnPhone();
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
            if (!validateInput(mailEdit)) return 1;
            else return 0;
        }

        private int doOnPhone() {
            if (!validateInput(phoneEdit)) return 2;
            else return 0;
        }
    }


}
