package pl.wmaciejewski.contactproject.database.dao;

import android.database.sqlite.SQLiteDatabase;

import pl.wmaciejewski.contactproject.database.MySQLConnector;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class PersonDAO {
    private SQLiteDatabase database;
    private String[] collumns={MySQLConnector.KEY_ID,MySQLConnector.KEY_NAME,MySQLConnector.KEY_SURNAME,MySQLConnector.KEY_EMAIL,
            MySQLConnector.KEY_NUMBERPHONE,MySQLConnector.KEY_IMAGE,MySQLConnector.KEY_SMALLIMAGE,MySQLConnector.KEY_GROUPFKID};

    private static String MY_TABLE = MySQLConnector.TABLE_PERSON;
}
