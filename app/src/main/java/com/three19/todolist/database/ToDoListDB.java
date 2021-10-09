package com.three19.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.three19.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class ToDoListDB extends DBConnection {

    public ToDoListDB(Context context) {
        super(context);
    }

    public ToDo add(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ToDo toDo = new ToDo();
        toDo.setName(name);

        ContentValues values = toDo.getContentValuesToAdd();
        Long id = db.insert("todolist", null, values);
        db.close();

        toDo.setId(id.intValue());

        return toDo;
    }

    public void update(ToDo toDo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = toDo.getContentValuesToUpdate();
        db.update("todolist", values, "id = ?", new String[]{String.valueOf(toDo.getId())});
        db.close();
    }

    public void remove(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM todolist WHERE id ="+ id);
        db.close();
    }

    public ToDo getDetails(int id) {
        String selectQuery = "SELECT * FROM campsites WHERE id = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        ToDo toDo = new ToDo();
        toDo.parse(cursor);

        db.close();
        return toDo;
    }

    public List<ToDo> getList() {
        List<ToDo> toDoList = new ArrayList<>();

        String selectQuery = "SELECT * FROM todolist";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ToDo toDo = new ToDo();
                toDo.parse(cursor);
                toDoList.add(toDo);
            } while (cursor.moveToNext());
        }

        db.close();
        return toDoList;
    }
}
