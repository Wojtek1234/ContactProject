package pl.wmaciejewski.contactproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;

import pl.wmaciejewski.contactproject.database.MySQLConnector;

/**
 * Created by w.maciejewski on 2014-10-28.
 */
public class MySQLConnectorTest extends AndroidTestCase

{
    private MySQLConnector connector;

    @Override
    protected void setUp() throws Exception {
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        connector = new MySQLConnector(context);
    }

    public void tearDown() throws Exception {
        connector.close();
        super.tearDown();
    }

    public void testCreatedTables() {
        final ArrayList<String> tableArray = new ArrayList<String>();
        SQLiteDatabase database = connector.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                tableArray.add(c.getString(0));
                c.moveToNext();
            }
        }

        assertTrue(tableArray.contains(MySQLConnector.TABLE_PERSON));
        assertTrue(tableArray.contains(MySQLConnector.TABLE_GROUP));


    }
}
