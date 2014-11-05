package pl.wmaciejewski.contactproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import pl.wmaciejewski.contactproject.createnewperson.CreateNewPersonActivity;
import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.database.entitys.Person;
import pl.wmaciejewski.contactproject.dialogs.ChooseActionDialog;
import pl.wmaciejewski.contactproject.modelView.ParcelPerson;
import pl.wmaciejewski.contactproject.modelView.PersonListAdapter;
import pl.wmaciejewski.contactproject.modelView.ViewModel;

public class MainActivity extends FragmentActivity implements ChooseActionDialog.onChooseActionListener {

    public static final int REQUEST_NEW_PERSON =267 ;
    public static final int REQUEST_EDIT_PERSON =268 ;
    public static final int REQUEST_CREATE_PERSON_NUMBER =100 ;
    public static final int REQUEST_EDIT_PERSON_NUMBER =101 ;

    public static final String REQUEST_CREATE_PERSON="REQUEST_MASSAGE";
    public static final String REQUEST_EDIT_CREATE_PERSON="REQUEST_MASSAGE";

    private ViewModel viewModel;
    private ListView list;
    private PersonListAdapter personListAdapter;
    private DatabaseProvider databaseProvider;
    private Person currentPerson;
    private ChooseActionDialog.onChooseActionListener onChooseActionListener;

    public ChooseActionDialog.onChooseActionListener getOnChooseActionListener() {
        return onChooseActionListener;
    }

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
        updatePersonAdapterList();
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityForResult();
            }
        });
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPerson=(Person) parent.getAdapter().getItem(position);
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
    private void openActivityForResult(Person person) {
        Intent intent = new Intent(getApplicationContext(), CreateNewPersonActivity.class);
        ParcelPerson parcelPerson=new ParcelPerson(person);
        intent.putExtra(REQUEST_EDIT_CREATE_PERSON,parcelPerson);
        startActivityForResult(intent, REQUEST_EDIT_PERSON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_NEW_PERSON){
            handleRequestNewPerson(resultCode, data);
        }else if(requestCode==REQUEST_EDIT_PERSON){
            if(resultCode==REQUEST_EDIT_PERSON_NUMBER){
                ParcelPerson parcelPerson=data.getParcelableExtra(REQUEST_CREATE_PERSON);
                viewModel.updatePerson(parcelPerson.getPerson());
                updatePersonAdapterList();
            }
        }
    }
    private void handleRequestNewPerson(int resultCode, Intent data) {
        if(resultCode==REQUEST_CREATE_PERSON_NUMBER){
            ParcelPerson parcelPerson=data.getParcelableExtra(REQUEST_CREATE_PERSON);
            viewModel.addPerson(parcelPerson.getPerson());
            updatePersonAdapterList();
        }
    }

    @Override
    public void onMailButtonClick(DialogFragment dialog) {
        sendEmailIntent(new String[]{currentPerson.getEmail()});
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                doOnEditAction();
                return true;
            case R.id.action_delete:

                    doOnDeleteAction();
                return true;
            case R.id.action_send_mail:

                    sendEmailIntent(getEmailsFromList());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doOnEditAction() {
        for(Person person:this.personListAdapter.getList()){
            if(person.isChecked()){
                openActivityForResult(person);
            }
           return;
        }
    }

    private String[] getEmailsFromList() {
        ArrayList<String> strings=new ArrayList<String>();
        for(Person person:this.personListAdapter.getList()){
            if(person.isChecked())strings.add(person.getEmail());
        }
        return strings.toArray(new String[strings.size()]);
    }

    private void sendEmailIntent(String[] adresses) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL,adresses);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client"));
    }

    private void doOnDeleteAction() {
        for(Person person:this.personListAdapter.getList()){
            if(person.isChecked())viewModel.delatePerson(person.getId());
        }
        updatePersonAdapterList();
    }

    private void updatePersonAdapterList() {
        this.personListAdapter=new PersonListAdapter(this, R.layout.single_contact_list_layout,viewModel.getPersonList());
        list.setAdapter(personListAdapter);
    }
}
