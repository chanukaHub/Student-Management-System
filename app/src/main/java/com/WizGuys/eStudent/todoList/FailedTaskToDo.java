package com.WizGuys.eStudent.todoList;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class FailedTaskToDo extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage mStorage;

    TextView task_data,task_date;

    private void initializeWidgets(){
        task_data = findViewById(R.id.task_data_ToDo_add);
        task_date= findViewById(R.id.task_date);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        initializeWidgets();

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Common.TASK_TABLE);

        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        updateUploadFile(id);
    }


    private void updateUploadFile(final String selectedKey) {
        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        String task=i.getExtras().getString("NAME_KEY");
        String dateToDo=i.getExtras().getString("DATE_KEY");
        String state=i.getExtras().getString("STATE_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");

        Task upload = new Task(id,task,dateToDo,state,email);

        //change state

        upload.setState(Common.TASK_FAILED);

        String uploadId = selectedKey;
        mDatabaseRef.child(uploadId).setValue(upload);
        Toast.makeText(FailedTaskToDo.this, "Task "+ upload.getTask() +" failed.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(FailedTaskToDo.this, ToDoList.class);
        startActivity(intent);

    }
}
