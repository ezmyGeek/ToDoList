package com.three19.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.three19.todolist.database.ToDoListDB;
import com.three19.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ToDoListDB toDoListDB;
    List<ToDo> arrayList;
    ToDoListAdapter adapter;
    ToDo selectedToDo;
    int selectedPosition;
    EditText txtName;
    Button addBtn;

    //added to support the update versus add functionality
    Boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (EditText) findViewById(R.id.txtName);

        toDoListDB = new ToDoListDB(this);
        arrayList = toDoListDB.getList();

        adapter = new ToDoListAdapter(this, (ArrayList<ToDo>) arrayList);

        ListView listView = (ListView) findViewById(R.id.lstView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                removeItemFromList(position);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedToDo = arrayList.get(position);
                selectedPosition = position;

                // 1. When the user clicks a list item, display the list item's text in the input text area
                txtName.setText(selectedToDo.getName());

                // add an appropriate addBtn() call here
                // 2. When a list item's text has been displayed in the input area, change the add button to an update button

                addBtn = (Button) findViewById(R.id.btnAdd);
                addBtn.setText("Update");
                update = true; //flip the switch to update mode


            }
        });

        addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = txtName.getText().toString();

                // add your code here
                // 3. When the update button is clicked, perform the update, clear the input text fleld,
                // and change the button back to add

                addBtn.setText("Add");
                if (name.trim().length()>0) {
                    if(update){
                    // this is an update of an existing item
                    update = false; //flip the switch back to false
                    selectedToDo.setName(txtName.getText().toString());
                    toDoListDB.update(selectedToDo);
                } else {
                        //the name is new not an update
                        ToDo toDo = toDoListDB.add(name);
                        arrayList.add(toDo);
                    }
                    // save the changes, clear the name entry field
                    adapter.notifyDataSetChanged();
                    txtName.setText("");
            }
        };
        });

        Button clearBtn = (Button) findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                reset();


            }
        });

        Button allBtn = (Button) findViewById(R.id.btnAll);
        allBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AllTasksActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void removeItemFromList(final int position)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToDo toDo = arrayList.get(position);

                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

                toDoListDB.remove(toDo.getId());
                reset();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    protected void reset() {
        txtName.setText("");

        // add an appropriate addBtn() call here

        selectedToDo = null;
        selectedPosition = -1;
    }
}

class ToDoListAdapter extends ArrayAdapter<ToDo>
{
    public ToDoListAdapter(Context context, ArrayList<ToDo> toDoList) {
        super(context, 0, toDoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDo toDo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(android.R.id.text1);
        name.setText(toDo.getName());

        return convertView;
    }
}