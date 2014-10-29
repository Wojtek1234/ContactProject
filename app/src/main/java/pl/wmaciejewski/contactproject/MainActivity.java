package pl.wmaciejewski.contactproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import pl.wmaciejewski.contactproject.database.entitys.Person;


public class MainActivity extends Activity {

    public static final int REQUEST_NEW_PERSON =267 ;
    public static final int REQUEST_CREATE_PERSON =100 ;

    private ListView list;
    private Button addBut,removeBut,editBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeGUI();

    }

    private void initializeGUI() {
        list=(ListView)findViewById(R.id.listview);
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

    private void openActivityForResult() {
        Intent intent = new Intent(getApplicationContext(), CreateNewPersonActivity.class);
        startActivityForResult(intent, REQUEST_NEW_PERSON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_NEW_PERSON){
            if(resultCode==REQUEST_CREATE_PERSON){
               
            }
        }
    }
}
