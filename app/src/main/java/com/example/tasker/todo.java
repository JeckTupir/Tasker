package com.example.tasker;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class todo extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itmesadap;
    private ListView listv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnadd = findViewById(R.id.buttonhome);
        listv = findViewById(R.id.list);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todo.this, Dashboard.class);
                startActivity(intent);
            }
        });

        Button btn1 = findViewById(R.id.add);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });


        Button btnPomodoro = findViewById(R.id.buttonfocus);
        // Set onClick Listener
        btnPomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Pomodoro activity
                Intent intent = new Intent(todo.this, Home.class);
                startActivity(intent);
            }
        });

        items = new ArrayList<>();
        itmesadap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listv.setAdapter(itmesadap);
        setUpListViewListener();
    }

    private void addItem(View v) {
        EditText input = findViewById(R.id.editText);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))) {
            itmesadap.add(itemText);
            input.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter text.", Toast.LENGTH_LONG).show();
        }

    }

    private void setUpListViewListener() {
        listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                itmesadap.notifyDataSetChanged();
                return true;
            }

        });

    }
}
