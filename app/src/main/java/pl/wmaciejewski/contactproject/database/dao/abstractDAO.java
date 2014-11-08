package pl.wmaciejewski.contactproject.database.dao;

import android.content.ContentValues;
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

    public  void create(T entity){
        ContentValues values = getContentValues(entity);
        database.insertOrThrow(MY_TABLE, null, values);
    }
    public  void updateEntity(T entity){

        String strFilter = getStringFilters(entity);
        ContentValues values = getContentValues(entity);
        database.update(MY_TABLE, values, strFilter, null);

    }

    protected abstract String getStringFilters(T entity);


    protected abstract ContentValues getContentValues(T entity);



    public T getById(Long id) {
        Cursor cursor = database.query(MY_TABLE, collumns, collumns[0] + "=" + id, null, null, null, null, null);
        cursor.moveToFirst();
        return cursorToEntity(cursor);
    }

    public abstract List<T> getByName(String name);


    public void removeAll() {
        database.delete(MY_TABLE, null, null);
    }

    public void removeById(Long id){database.delete(MY_TABLE,collumns[0] + "=?",new String[]{Long.toString(id)});}



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
