package com.example.tasker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_to_do);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText title; // = findViewById(R.id.title);
        EditText des; // = findViewById(R.id.des);
        Button add; //= findViewById(R.id.add);

        setContentView(R.layout.activity_add_to_do);

        title = findViewById(R.id.title);
        des = findViewById(R.id.des);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlE = title.getText().toString();
                String description = des.getText().toString();

                Intent intent = new Intent(AddToDo.this, todo.class);
                intent.putExtra("title", titlE);
                intent.putExtra("description", description);
                startActivity(intent);
            }
        });

    }
}