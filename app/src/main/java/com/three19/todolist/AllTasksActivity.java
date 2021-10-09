package com.three19.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.three19.todolist.database.ToDoListDB;
import com.three19.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        setTitle("All Tasks");

        ToDoListDB toDoListDB = new ToDoListDB(this);
        List< ToDo > arrayList = toDoListDB.getList();

        ToDoListAdapter adapter = new ToDoListAdapter(this, (ArrayList<ToDo>) arrayList);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(adapter);

        Button backBtn = (Button) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
