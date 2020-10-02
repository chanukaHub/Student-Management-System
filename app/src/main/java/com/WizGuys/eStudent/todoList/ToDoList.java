package com.WizGuys.eStudent.todoList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.WizGuys.eStudent.R;

public class ToDoList extends AppCompatActivity {

    private ImageButton addButton;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        addButton = findViewById(R.id.add_task);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoList.this, ToDoForm.class);
                startActivity(intent);
            }
        });
    }
}