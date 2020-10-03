package com.WizGuys.eStudent.todoList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToDoForm extends AppCompatActivity {

    TextView taskData;
    TextView taskDate;
    Button addTaskButton;

    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);

        taskData = findViewById(R.id.task_data);
        taskDate = findViewById(R.id.task_date);
        addTaskButton = findViewById(R.id.task_addToDo);

        reff = FirebaseDatabase.getInstance().getReference().child("Task");


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskData.getText().toString();
                String date1 = taskDate.getText().toString();
                Task newTask = new Task(task, date1, Common.email);

                reff.push().setValue(newTask);
                Toast.makeText(ToDoForm.this, "Task added successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ToDoForm.this, ToDoList.class);
                startActivity(intent);
            }
        });
    }
}