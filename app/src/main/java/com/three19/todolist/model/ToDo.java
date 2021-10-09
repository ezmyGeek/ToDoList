package com.three19.todolist.model;

import android.content.ContentValues;
import android.database.Cursor;

public class ToDo {
    int _id;
    String _name;

    public int getId() {
        return _id;
    }
    public void setId(int value) {
        _id = value;
    }

    public String getName() {
        return _name;
    }
    public void setName(String value) {
        _name = value;
    }

    public ContentValues getContentValuesToAdd(){
        ContentValues values = new ContentValues();
        values.put("name", getName());
        return values;
    }

    public ContentValues getContentValuesToUpdate(){
        ContentValues values = new ContentValues();
        values.put("id", getId());
        values.put("name", getName());
        return values;
    }

    public void parse(Cursor cursor) {
        setId(cursor.getInt(0));
        setName(cursor.getString(1));
    }
}
