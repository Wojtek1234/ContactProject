package pl.wmaciejewski.contactproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class MySQLConnector extends SQLiteOpenHelper {
    //Table
    public static final String TABLE_PERSON = "person";
    public static final String TABLE_GROUP = "contactgroup";
    //Colums
    public static final String KEY_GROUPID = "group_id";
    public static final String GROUPID_OPTIONS = "INTEGER PRIMARY KEY AutoIncrement";
    public static final int GROUPID_COLUMN = 0;
    public static final String KEY_GROUPNAME = "groupname";
    public static final String GROUPNAME_OPTIONS = "TEXT NOT NULL UNIQUE";
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUP + " (" +
            KEY_GROUPID + " " + GROUPID_OPTIONS + ", " +
            KEY_GROUPNAME + " " + GROUPNAME_OPTIONS +
            " );";
    public static final int GROUPNAME_COLUMN = 1;
    //Colums
    public static final String KEY_ID = "id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AutoIncrement";
    public static final int ID_COLUMN = 0;
    public static final String KEY_NAME = "name";
    public static final String NAME_OPTIONS = "TEXT NOT NULL";
    public static final int NAME_COLUMN = 1;
    public static final String KEY_SURNAME = "surname";
    public static final String SURNAME_OPTIONS = "TEXT";
    public static final int SURNAME_COLUMN = 2;
    public static final String KEY_EMAIL = "email";
    public static final String EMAIL_OPTIONS = "TEXT";
    public static final int EMAIL_COLUMN = 3;
    public static final String KEY_NUMBERPHONE = "phone";
    public static final String NUMBERPHONE_OPTIONS = "TEXT";
    public static final int NUMBERPHONE_COLUMN = 4;
    public static final String KEY_IMAGE = "bigimage";
    public static final String IMAGE_OPTIONS = "TEXT";
    public static final int IMAGE_COLUMN = 5;
    public static final String KEY_SMALLIMAGE = "smallimage";
    public static final String SMALLIMAGE_OPTIONS = "BLOB";
    public static final int SMALLIMAGE_COLUMN = 5;
    public static final String KEY_GROUPFKID = "fk_group_id";
    public static final String GROUPFKID_OPTIONS = "INTEGER, FOREIGN KEY(" + KEY_GROUPFKID + ") REFERENCES " + TABLE_GROUP + "(" + KEY_GROUPID + ")";
    private static final String CREATE_TABLE_PERSON = "CREATE TABLE " + TABLE_PERSON + " (" +
            KEY_ID + " " + ID_OPTIONS + ", " +
            KEY_NAME + " " + NAME_OPTIONS + ", " +
            KEY_SURNAME + " " + SURNAME_OPTIONS + ", " +
            KEY_EMAIL + " " + EMAIL_OPTIONS + ", " +
            KEY_NUMBERPHONE + " " + NUMBERPHONE_OPTIONS + ", " +
            KEY_IMAGE + " " + IMAGE_OPTIONS + ", " +
            KEY_SMALLIMAGE + " " + SMALLIMAGE_OPTIONS + ", " +
            KEY_GROUPFKID + " " + GROUPFKID_OPTIONS +
            " );";
    public static final int GROUPFKID_COLUMN = 5;

    //Table


    // Table Create Statements
    private static final String DATABASE_NAME = "conntacts.db";
    private static final int DATABASE_VERSION = 1;
    private static MySQLConnector ourInstance;


    public static MySQLConnector getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new MySQLConnector(context);
            return ourInstance;
        }else  return ourInstance;
    }


    public MySQLConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);

        onCreate(sqLiteDatabase);
    }
}
