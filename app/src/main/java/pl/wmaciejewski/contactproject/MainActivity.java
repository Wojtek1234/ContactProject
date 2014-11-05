package pl.wmaciejewski.contactproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;

import pl.wmaciejewski.contactproject.createnewperson.CreateNewPersonActivity;
import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.entitys.Person;
import pl.wmaciejewski.contactproject.dialogs.ChooseActionDialog;
import pl.wmaciejewski.contactproject.modelView.ParcelPerson;
import pl.wmaciejewski.contactproject.modelView.PersonListAdapter;
import pl.wmaciejewski.contactproject.modelView.ViewModel;

public class MainActivity extends FragmentActivity implements ChooseActionDialog.onChooseActionListener {

    public static final int REQUEST_NEW_PERSON =267 ;
    public static final int REQUEST_EDIT_PERSON =267 ;
    public static final int REQUEST_CREATE_PERSON_NUMBER =100 ;
    public static final int REQUEST_EDIT_PERSON_NUMBER =101 ;
    public static final String REQUEST_CREATE_PERSON="REQUEST_MASSAGE";

    private ViewModel viewModel;
    private ListView list;
    private ImageButton addBut;
    private PersonListAdapter personListAdapter;
    private DatabaseProvider databaseProvider;
    private Person currentPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeGUI();
    }

    private void initializeGUI() {
        databaseProvider=DatabaseProvider.getInstance(getApplicationContext());
        openDataBase(databaseProvider);
        viewModel=new ViewModel(databaseProvider);
        list=(ListView)findViewById(R.id.listview);
        list.setFastScrollEnabled(true);
        this.personListAdapter=new PersonListAdapter(this,R.layout.single_contact_list_layout,viewModel.getPersonList());
        list.setAdapter(personListAdapter);
        addBut=(ImageButton)findViewById(R.id.addButton);
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityForResult();
            }
        });
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPerson=(Person) parent.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, "Clicked " + currentPerson.getName(), Toast.LENGTH_SHORT).show();
                ChooseActionDialog chooseActionDialog=new ChooseActionDialog();
                chooseActionDialog.show(getSupportFragmentManager(), getResources().getString(R.string.choose_action));
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseProvider.close();
    }

    private void openDataBase(DatabaseProvider databaseProvider) {
        try {  databaseProvider.open();
        } catch (SQLException e) { e.printStackTrace();}
    }

    private void openActivityForResult() {
        Intent intent = new Intent(getApplicationContext(), CreateNewPersonActivity.class);
        startActivityForResult(intent,REQUEST_NEW_PERSON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_NEW_PERSON){
            handleRequestNewPerson(resultCode, data);
        }else if(requestCode==REQUEST_EDIT_PERSON){

        }
    }

    private void handleRequestNewPerson(int resultCode, Intent data) {
        if(resultCode==REQUEST_CREATE_PERSON_NUMBER){
            ParcelPerson parcelPerson=data.getParcelableExtra(REQUEST_CREATE_PERSON);
            viewModel.addPerson(parcelPerson.getPerson());
            this.personListAdapter=new PersonListAdapter(this, R.layout.single_contact_list_layout,viewModel.getPersonList());
            list.setAdapter(personListAdapter);
        }
    }

    @Override
    public void onMeilButtonClick(DialogFragment dialog) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{currentPerson.getEmail()});
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client"));
    }

    @Override
    public void onSMSButtonClick(DialogFragment dialog) {

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", currentPerson.getPhoneNumber(), null)));
    }

    @Override
    public void onCallButtonClick(DialogFragment dialog) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentPerson.getPhoneNumber()));
        startActivity(intent);
    }


}
