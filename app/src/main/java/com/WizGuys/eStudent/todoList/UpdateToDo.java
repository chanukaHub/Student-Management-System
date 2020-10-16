package com.WizGuys.eStudent.todoList;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageTask;
import com.WizGuys.eStudent.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateToDo extends AppCompatActivity {

    private Button uploadBtn, selectDateButton;
    private TextView taskData, taskDate;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private FirebaseStorage mStorage;
    DatePickerDialog.OnDateSetListener setListener;
    TextView task_data,task_date;

    private void initializeWidgets(){
        task_data = findViewById(R.id.task_data_ToDo_add);
        task_date= findViewById(R.id.task_date);
        selectDateButton = findViewById(R.id.textView4);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);
        initializeWidgets();

        mStorage = FirebaseStorage.getInstance();

        uploadBtn = findViewById(R.id.add_task_img_ToDo);

        //ET Text
        taskData = findViewById(R.id.task_data_ToDo_add);
        taskDate = findViewById ( R.id.task_date);
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
                        UpdateToDo.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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


                mDatabaseRef = FirebaseDatabase.getInstance().getReference(Common.TASK_TABLE);

        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        final String task=i.getExtras().getString("NAME_KEY");
        final String dateToDo=i.getExtras().getString("DATE_KEY");


        taskData.setText(task);
        taskDate.setText(dateToDo);


        final String selectedKey  = id;

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateToDo.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {

                    String today = getDateToday();
                    String taskDay = taskDate.getText().toString();

                    String[] todayParts = today.split("/");
                    int todayDay = Integer.parseInt(todayParts[0]);
                    int todayMonth = Integer.parseInt(todayParts[1]);
                    int todayYear = Integer.parseInt(todayParts[2]);

                    String[] taskDayParts = taskDay.split("/");
                    int tskDay = Integer.parseInt(taskDayParts[0]);
                    int tskMonth = Integer.parseInt(taskDayParts[1]);
                    int tskYear = Integer.parseInt(taskDayParts[2]);

                    if (todayYear <= tskYear && todayMonth <= tskMonth && todayDay <= tskDay){
                        updateUploadFile(selectedKey);
                    } else {
                        Toast.makeText(UpdateToDo.this, "Invalid date.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private void updateUploadFile(final String selectedKey) {
        Intent i=this.getIntent();

        String state=i.getExtras().getString("STATE_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");
        Task upload = new Task(
                taskData.getText().toString().trim(),
                taskDate.getText().toString(),email,state


        );
        String uploadId = selectedKey;

        mDatabaseRef.child(uploadId).setValue(upload);
        Toast.makeText(UpdateToDo.this, "Update Success", Toast.LENGTH_SHORT).show();

        taskData.setText("");
        taskDate.setText("");

        Intent intent = new Intent(UpdateToDo.this, ToDoList.class);
        startActivity(intent);

    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

}