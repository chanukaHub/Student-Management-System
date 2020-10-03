package com.WizGuys.eStudent.todoList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Task;
import com.WizGuys.eStudent.model.Teacher;
import com.WizGuys.eStudent.teachers.Update;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UpdateToDoForm extends AppCompatActivity {

    TextView taskData;
    TextView taskDate;
    Button addTaskButton;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);

        taskData = findViewById(R.id.task_data_toDoForm);
        taskDate = findViewById(R.id.task_date_toDoForm);
        addTaskButton = findViewById(R.id.task_addToDo);

        reff = FirebaseDatabase.getInstance().getReference().child("Task");


        Intent i = this.getIntent();
        String id = i.getExtras().getString("ID_KEY");
        String name = i.getExtras().getString("NAME_KEY");
        String date = i.getExtras().getString("DATE_KEY");
        String state = i.getExtras().getString("STATE_KEY");
        String emails = i.getExtras().getString("EMAIL_KEY");

        taskData.setText(name);
        taskDate.setText(date);
        final String selectedKey = id;

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskData.getText().toString();
                String date1 = taskDate.getText().toString();
                Task newTask = new Task(task, date1, Common.email);

                reff.push().setValue(newTask);
                Toast.makeText(UpdateToDoForm.this, "Task added successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateToDoForm.this, ToDoList.class);
                updateUploadFile(selectedKey);
            }
        });
    }

    private void updateUploadFile(final String selectedKey) {




        Task upload = new Task(taskData.getText().toString().trim(),
                taskDate.getText().toString(),
                Common.email
        );
        String uploadId = selectedKey;
        mDatabaseRef.child(uploadId).setValue(upload);
    }

}