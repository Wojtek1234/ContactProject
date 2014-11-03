package pl.wmaciejewski.contactproject.modelView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-29.
 */
public class PersonListAdapter extends ArrayAdapter<Person> implements SectionIndexer {

    private final Context context;
    private List<Person> list;
    String[] sections;
    private HashMap<String, Integer> mapIndex;

    public PersonListAdapter(Activity context, int resource, List<Person> objects) {
        super(context, resource, objects);
        this.list=objects;
        this.context=context;
        for (int x = 0; x < this.list.size(); x++) {
            String name = this.list.get(x).getName();
            String ch = name.substring(0, 1);
            ch = ch.toUpperCase();

            // HashMap will prevent duplicates
            mapIndex.put(ch, x);
        }
        Set<String> sectionLetters = mapIndex.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Object[] getSections() {
        return  this.sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
         return mapIndex.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
