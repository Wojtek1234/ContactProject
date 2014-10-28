package pl.wmaciejewski.contactproject.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import pl.wmaciejewski.contactproject.database.MySQLConnector;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class GroupDAO {
    private SQLiteDatabase database;
    private MySQLConnector sqlConnector;

    private String[] collumns={MySQLConnector.KEY_GROUPID,MySQLConnector.KEY_GROUPNAME};


    public GroupDAO(Context context) {
        sqlConnector = new MySQLConnector(context);
    }


    public void open() throws SQLException{
        database=sqlConnector.getWritableDatabase();
    }

    public void close(){
       sqlConnector.close();
    }

    public void createGroup(String groupName){
        ContentValues values = new ContentValues();
        values.put(collumns[1],groupName);
        database.insertOrThrow(MySQLConnector.TABLE_GROUP,null,values);
    }



}
