package pl.wmaciejewski.contactproject.modelView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import pl.wmaciejewski.contactproject.R;
import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-29.
 */
public class PersonListAdapter extends ArrayAdapter<Person> implements SectionIndexer {

    private final Activity context;
    private final BitmapByteChanger bitmapByteChanger;
    private List<Person> list;
    String[] sections;
    private HashMap<String, Integer> mapIndex;

    public PersonListAdapter(Activity context, int resource, List<Person> objects) {
        super(context, resource, objects);
        this.list=objects;
        this.context=context;
        this.bitmapByteChanger=new BitmapByteChanger();
        if(this.list.size()>0){
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            view= layoutInflater.inflate(R.layout.single_contact_list_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)view.findViewById(R.id.nameAndSurnameTV);
            viewHolder.checkBox=(CheckBox)view.findViewById(R.id.checkBoxContact);
            viewHolder.imageView=(ImageView)view.findViewById(R.id.imageSmallContact);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final Person person=getPerson(position);


        TextView textView=viewHolder.textView;
        textView.setText(person.getName()+" "+person.getSurname());
        ImageView iv= viewHolder.imageView;
        iv.setImageBitmap(bitmapByteChanger.ByteArrayToBitmap(person.getSmallImage()));
        CheckBox checkBox=viewHolder.checkBox;
        checkBox.setChecked(person.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                person.setChecked(isChecked);
            }
        });
        return view;
    }

    private Person getPerson(int position) {
        return this.list.get(position);
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
    class ViewHolder{
        TextView textView;
        ImageView imageView;
        CheckBox checkBox;
    }
}
