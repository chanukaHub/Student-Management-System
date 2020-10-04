package com.WizGuys.eStudent.todoList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.WizGuys.eStudent.R;

public class TaskFailedNotificationToDo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_failed_notification_to_do);

        TextView textView = findViewById(R.id.messageNoti);

        String message = getIntent().getStringExtra("message");
        textView.setText(message);

    }
}