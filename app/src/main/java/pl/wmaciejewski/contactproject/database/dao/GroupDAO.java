package pl.wmaciejewski.contactproject.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import pl.wmaciejewski.contactproject.database.MySQLConnector;
import pl.wmaciejewski.contactproject.database.entitys.Group;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class GroupDAO extends abstractDAO<Group> {


    public GroupDAO(SQLiteDatabase database) {
        super(database);
        collumns = new String[]{MySQLConnector.KEY_GROUPID, MySQLConnector.KEY_GROUPNAME};
        MY_TABLE = MySQLConnector.TABLE_GROUP;
    }




    @Override
    protected ContentValues getContentValues(Group entity) {
        ContentValues values = new ContentValues();
        values.put(collumns[1], entity.getGroupName());
        return  values;
    }


    @Override
    public List<Group> getByName(String name) {
        String[] strings = {name};
        Cursor cursor = database.query(MY_TABLE, collumns, collumns[1] + "= ?", strings, null, null, null, null);
        cursor.moveToFirst();
        List<Group> groups = rewriteToList(cursor);
        return groups;
    }

    @Override
    public void updateEntity(Group entity) {

        String strFilter = collumns[0]+"=" + entity.getId();
        ContentValues values = new ContentValues();
        values.put(collumns[1], entity.getGroupName());
        database.update(MY_TABLE, values, strFilter, null);

    }

    @Override
    protected String getStringFilters(Group entity) {
        return collumns[0]+"=" + entity.getId();
    }


    public void create(String groupName) {
        ContentValues values = new ContentValues();
        values.put(collumns[1], groupName);
        database.insertOrThrow(MY_TABLE, null, values);
    }


    @Override
    protected Group cursorToEntity(Cursor cursor) {
        return new Group(cursor.getLong(0), cursor.getString(1));
    }


}
