package pl.wmaciejewski.contactproject.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by w.maciejewski on 2014-10-29.
 */
public abstract class abstractDAO<T> {
    protected String MY_TABLE;
    protected SQLiteDatabase database;
    protected String[] collumns;

    public abstractDAO(SQLiteDatabase database) {
        this.database = database;
    }

    public abstract void create(T entity);

    public T getById(Long id) {
        Cursor cursor = database.query(MY_TABLE, collumns, collumns[0] + "=" + id, null, null, null, null, null);
        cursor.moveToFirst();
        return cursorToEntity(cursor);
    }

    public abstract List<T> getByName(String name);


    public void removeAll() {
        database.delete(MY_TABLE, null, null);
    }

    ;

    public List<T> getAll() {
        Cursor cursor = database.query(MY_TABLE,
                collumns, null, null, null, null, collumns[1] + " COLLATE NOCASE;");

        cursor.moveToFirst();
        List<T> groups = rewriteToList(cursor);
        cursor.close();
        return groups;
    }


    protected List<T> rewriteToList(Cursor cursor) {
        List<T> groups = new ArrayList<T>();
        while (!cursor.isAfterLast()) {
            T comment = cursorToEntity(cursor);
            groups.add(comment);
            cursor.moveToNext();
        }
        return groups;
    }

    protected abstract T cursorToEntity(Cursor cursor);


}
