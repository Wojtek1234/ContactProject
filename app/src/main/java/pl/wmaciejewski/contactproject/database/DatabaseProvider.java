package pl.wmaciejewski.contactproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by w.maciejewski on 2014-10-29.
 */
public class DatabaseProvider {


    private final MySQLConnector sqlConnector;
    private static DatabaseProvider instance = null;

    private SQLiteDatabase database;

    public static DatabaseProvider getInstance(Context context) {
        if(instance == null){
            instance = new DatabaseProvider(context);
            return instance;
        }else  return instance;
    }

    private DatabaseProvider(Context context) {
        this.sqlConnector =  new MySQLConnector(context);
    }

    public void open() throws SQLException {
        this.database = sqlConnector.getWritableDatabase();
    }

    public void close() {
        sqlConnector.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
