package pl.wmaciejewski.contactproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;

import pl.wmaciejewski.contactproject.createnewperson.CreateNewPersonActivity;
import pl.wmaciejewski.contactproject.database.DatabaseProvider;
import pl.wmaciejewski.contactproject.modelView.ParcelPerson;
import pl.wmaciejewski.contactproject.modelView.PersonListAdapter;
import pl.wmaciejewski.contactproject.modelView.ViewModel;


public class MainActivity extends Activity {

    public static final int REQUEST_NEW_PERSON =267 ;
    public static final int REQUEST_CREATE_PERSON_NUMBER =100 ;
    public static final String REQUEST_CREATE_PERSON="REQUEST_MASSAGE";

    private ViewModel viewModel;
    private ListView list;
    private Button addBut,removeBut,editBut;
    private PersonListAdapter personListAdapter;
    private DatabaseProvider databaseProvider;

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
        addBut=(Button)findViewById(R.id.addButton);
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityForResult();
            }
        });
        removeBut=(Button)findViewById(R.id.removeButton);
        removeBut=(Button)findViewById(R.id.removeButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseProvider.close();
    }

    private void openDataBase(DatabaseProvider databaseProvider) {
        try {
            databaseProvider.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openActivityForResult() {
        Intent intent = new Intent(getApplicationContext(), CreateNewPersonActivity.class);




        startActivityForResult(intent,REQUEST_NEW_PERSON);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_NEW_PERSON){
            if(resultCode==REQUEST_CREATE_PERSON_NUMBER){
                ParcelPerson parcelPerson=data.getParcelableExtra(REQUEST_CREATE_PERSON);
                viewModel.addPerson(parcelPerson.getPerson());
                this.personListAdapter=new PersonListAdapter(this,R.layout.single_contact_list_layout,viewModel.getPersonList());
                list.setAdapter(personListAdapter);
            }
        }
    }
}
