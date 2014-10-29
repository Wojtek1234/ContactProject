package pl.wmaciejewski.contactproject.database.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.List;

import pl.wmaciejewski.contactproject.database.MySQLConnector;
import pl.wmaciejewski.contactproject.database.entitys.Person;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class PersonDAO extends abstractDAO<Person> {


    public PersonDAO(SQLiteDatabase database) {
        super(database);
        collumns = new String[]{MySQLConnector.KEY_ID, MySQLConnector.KEY_NAME, MySQLConnector.KEY_SURNAME, MySQLConnector.KEY_EMAIL,
                MySQLConnector.KEY_NUMBERPHONE, MySQLConnector.KEY_IMAGE, MySQLConnector.KEY_SMALLIMAGE, MySQLConnector.KEY_GROUPFKID};
        MY_TABLE = MySQLConnector.TABLE_PERSON;
    }

    @Override
    public void create(Person entity) {
        ContentValues values = new ContentValues();
        values.put(collumns[1], entity.getName());
        values.put(collumns[2], entity.getSurname());
        values.put(collumns[3], entity.getEmail());
        values.put(collumns[4], entity.getPhoneNumber());
        if (entity.getImage() == null) values.put(collumns[5], (byte[]) null);
        else values.put(collumns[5], entity.getImage().toString());
        values.put(collumns[6], entity.getSmallImage());
        values.put(collumns[7], entity.getGroupId());

        database.insertOrThrow(MY_TABLE, null, values);
    }



    @Override
    public List<Person> getByName(String name) {
        String[] strings = {name};
        Cursor cursor = database.query(MY_TABLE, collumns, collumns[1] + "= ?", strings, null, null, null, null);
        cursor.moveToFirst();
        List<Person> persons = rewriteToList(cursor);
        Cursor cursor2 = database.query(MY_TABLE, collumns, collumns[2] + "= ?", strings, null, null, null, null);
        cursor2.moveToFirst();
        List<Person> persons2 = rewriteToList(cursor2);
        for (Person person : persons2) {
            persons.add(person);
        }
        return persons;
    }


    @Override
    protected Person cursorToEntity(Cursor cursor) {
        return new Person(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                , cursor.getString(4), cursor.getString(5), cursor.getBlob(6), cursor.getLong(7));
    }

    public List<Person> getByGropID(long id) {


        Cursor cursor = database.query(MY_TABLE, collumns, collumns[7] + "=" + id, null, null, null, collumns[1] + " COLLATE NOCASE;");
        cursor.moveToFirst();

        return rewriteToList(cursor);
    }

}
