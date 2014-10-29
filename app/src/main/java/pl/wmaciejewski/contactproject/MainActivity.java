package pl.wmaciejewski.contactproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity {

    private ListView list;
    private Button addBut,removeBut,editBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=(ListView)findViewById(R.id.listview);
        addBut=(Button)findViewById(R.id.addButton);
        removeBut=(Button)findViewById(R.id.removeButton);
        removeBut=(Button)findViewById(R.id.removeButton);

    }



}
