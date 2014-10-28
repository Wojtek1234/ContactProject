package pl.wmaciejewski.contactproject.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.contactproject.database.MySQLConnector;
import pl.wmaciejewski.contactproject.database.entitys.Group;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class GroupDAO {


    private SQLiteDatabase database;
    //For test purpose
    private static String MY_TABLE = MySQLConnector.TABLE_GROUP;

    public SQLiteDatabase getDatabase() {
        return database;
    }

    private MySQLConnector sqlConnector;

    private String[] collumns = {MySQLConnector.KEY_GROUPID, MySQLConnector.KEY_GROUPNAME};


    public GroupDAO(Context context) {
        sqlConnector = new MySQLConnector(context);
    }


    public void open() throws SQLException {
        database = sqlConnector.getWritableDatabase();
    }

    public void close() {
        sqlConnector.close();
    }

    public void createGroup(String groupName) {
        ContentValues values = new ContentValues();
        values.put(collumns[1], groupName);
        database.insertOrThrow(MY_TABLE, null, values);
    }


    public void removeAll() {
        database.delete(MY_TABLE, null, null);
    }

    public List<Group> getAllGroups() {


        Cursor cursor = database.query(MY_TABLE,
                collumns, null, null, null, null, null);

        cursor.moveToFirst();
        List<Group> groups = rewriteToList(cursor);
        cursor.close();
        return groups;
    }

    private List<Group> rewriteToList(Cursor cursor) {
        List<Group> groups = new ArrayList<Group>();
        while (!cursor.isAfterLast()) {
            Group comment = cursorToGroup(cursor);
            groups.add(comment);
            cursor.moveToNext();
        }
        return groups;
    }

    private Group cursorToGroup(Cursor cursor) {
        return new Group(cursor.getLong(0), cursor.getString(1));
    }

    public Group getGroup(String test_name) {
        return null;
    }
}
