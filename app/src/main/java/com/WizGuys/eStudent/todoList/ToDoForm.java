package com.WizGuys.eStudent.todoList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.Login;
import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ToDoForm extends AppCompatActivity {

    TextView taskData;
    TextView taskDate;

    Button addTaskButton, selectDateButton;

    DatePickerDialog.OnDateSetListener setListener;

    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);

        taskData = findViewById(R.id.task_data_ToDo_add);
        taskDate = findViewById(R.id.task_date);
        addTaskButton = findViewById(R.id.add_task_img_ToDo);
        selectDateButton = findViewById(R.id.textView4);

        //date picker
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ToDoForm.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,year,month,day
                );

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" +month + "/" + year;

                taskDate.setText(date);
            }
        };


        reff = FirebaseDatabase.getInstance().getReference().child(Common.TASK_TABLE);


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskData.getText().toString();
                String date1 = taskDate.getText().toString();
                Task newTask = new Task(task, date1, Common.email);

                if (newTask.getTask().isEmpty() || newTask.getDate().isEmpty()){
                    Toast.makeText(ToDoForm.this, "Please fill the form.", Toast.LENGTH_LONG).show();
                } else {

                    if (Common.email.equals(Common.loggedOut)){
                        Toast.makeText(ToDoForm.this, "Please log into your account before add task.", Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(ToDoForm.this, Login.class);
                        startActivity(intent);
                    } else {
                        reff.push().setValue(newTask);

                        //success message
                        Toast.makeText(ToDoForm.this, "Task added successfully.", Toast.LENGTH_LONG).show();


                        //dialog box
                        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoForm.this);

                        builder.setMessage("Do you want to add another task?");
                        builder.setTitle("Add another...");
                        //user needs select choice
                        builder.setCancelable(false);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                taskData.setText("");
                                taskDate.setText("");
                                dialogInterface.cancel();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ToDoForm.this, ToDoList.class);
                                startActivity(intent);
                            }
                        });

                        //create alert dialog
                        AlertDialog alertDialog = builder.create();

                        //show alert dialog
                        alertDialog.show();
                    }

                }
            }
        });
    }
}