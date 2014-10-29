package pl.wmaciejewski.contactproject.modelView;

import android.content.Context;
import android.widget.ArrayAdapter;
import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-29.
 */
public class CustomAdapter extends ArrayAdapter<Person>{

    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }



}
