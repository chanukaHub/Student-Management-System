package com.WizGuys.eStudent.todoList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.Dashboard;
import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.ToDoAdapter;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoList extends AppCompatActivity implements ToDoAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ToDoAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Task> mTasks;
    private ImageView backImg;

    private ImageView addTaskImgToDo;
    private TextView pendingText, finishedText, failedText;


    private void updateActivity(String[] data){
        Intent intent = new Intent(this, UpdateToDo.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("DATE_KEY",data[2]);
        intent.putExtra("STATE_KEY",data[3]);
        intent.putExtra("EMAIL_KEY",data[4]);
        startActivity(intent);
    }

    private void confirmActivity(String[] data){
        Intent intent = new Intent(ToDoList.this, ConfirmToDo.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("DATE_KEY",data[2]);
        intent.putExtra("STATE_KEY",data[3]);
        intent.putExtra("EMAIL_KEY",data[4]);
        startActivity(intent);
    }

    private void faildTaskUpdate(String[] data){
        Intent intent = new Intent(ToDoList.this, FailedTaskToDo.class);
        intent.putExtra("ID_KEY",data[0]);
        intent.putExtra("NAME_KEY",data[1]);
        intent.putExtra("DATE_KEY",data[2]);
        intent.putExtra("STATE_KEY",data[3]);
        intent.putExtra("EMAIL_KEY",data[4]);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_to_do_list );

        mRecyclerView = findViewById(R.id.task_list_ToDo);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progressbar_ToDo);
        mProgressBar.setVisibility(View.VISIBLE);
        backImg = findViewById(R.id.back_ToDoList_form);

        //set back button
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoList.this, Dashboard.class);
                startActivity(intent);
            }
        });

        mTasks = new ArrayList<> ();
        mAdapter = new ToDoAdapter (ToDoList.this, mTasks);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ToDoList.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Common.TASK_TABLE);
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTasks.clear();

                //get today

                String today = getDateToday();
                String[] todayParts = today.split("/");
                int today_day = Integer.parseInt(todayParts[0]);
                int today_month = Integer.parseInt(todayParts[1]);
                int today_year = Integer.parseInt(todayParts[2]);
                if (!Common.email.equals(Common.loggedOut)){

                    int totalTasks = 0;
                    int pending = 0;
                    int finished = 0;
                    int failed = 0;



                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        Task upload = taskSnapshot.getValue(Task.class);
                        upload.setTaskKey(taskSnapshot.getKey());

                        totalTasks++;

                        if (upload.getState().equals(Common.TASK_FAILED)){
                            failed++;
                        }

                        if (upload.getState().equals(Common.TASK_FINISHED)){
                            finished++;
                        }

                        if (upload.getState().equals(Common.TASK_UNFINISHED)){
                            pending++;
                        }

                        if (upload.getState().equals(Common.TASK_UNFINISHED) && upload.getUserEmail().equals(Common.email)){


                            String taskDay = upload.getDate();
                            String[] taskdayParts = taskDay.split("/");
                            int taskday_day = Integer.parseInt(taskdayParts[0]);
                            int taskday_month = Integer.parseInt(taskdayParts[1]);
                            int taskday_year = Integer.parseInt(taskdayParts[2]);

                                if (today_year > taskday_year){
                                    failedTask(upload);
                                } else if (today_year == taskday_year && today_month > taskday_month) {
                                    failedTask(upload);
                                } else if (today_year == taskday_year && today_month == taskday_month && today_day > taskday_day){
                                    failedTask(upload);
                                } else if (today_day == taskday_day && today_month == taskday_month && today_year == taskday_year){
                                        mTasks.add(upload);
                                    }

                        }

                    }


                    //calculate tasks
                    float failedPcent, finishedPecent = 0;
                    failedPcent = calcPecent(failed, totalTasks);
                    finishedPecent = calcPecent(finished, totalTasks);

                    pendingText = findViewById(R.id.num_of_pending);
                    finishedText = findViewById(R.id.num_of_finished);
                    failedText = findViewById(R.id.num_fo_failed);

                    pendingText.setText(String.valueOf(pending));
                    finishedText.setText(( (int) finishedPecent)+"%");
                    failedText.setText(( (int) failedPcent)+"%");


                    mAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(ToDoList.this, "Please log into your account.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ToDoList.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                     mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        //addButton
        addTaskImgToDo = findViewById(R.id.add_task_img_ToDo);

        addTaskImgToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoList.this, ToDoForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onShowItemClick(int position) {
        Task task1=mTasks.get(position);
        final String selectedKey = task1.getTaskKey();
        String[] taskData={
                selectedKey,
                task1.getTask(),
                task1.getDate(),
                task1.getState(),
                task1.getUserEmail()

        };

        updateActivity(taskData);
    }

    @Override
    public void onConfirmItemClick(int position) {
        Task selectedItem = mTasks.get(position);
        final String selectedKey = selectedItem.getTaskKey();

        //change state
        selectedItem.setState(Common.TASK_FINISHED);

        String[] taskdata = {
                selectedKey,
                selectedItem.getTask(),
                selectedItem.getDate(),
                selectedItem.getState(),
                selectedItem.getUserEmail()
        };

        confirmActivity(taskdata);

    }
    @Override
    public void onDeleteItemClick(int position) {
        Task selectedItem = mTasks.get(position);
        final String selectedKey = selectedItem.getTaskKey();

        //dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoList.this);

        builder.setMessage("Do you want to delete this task?");
        builder.setTitle("Delete Warning!");
        //user needs select choice
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ToDoList.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        //create alert dialog
        AlertDialog alertDialog = builder.create();

        //show alert dialog
        alertDialog.show();

    }

    private void failedTask (Task task){
        final String selecteKey = task.getTaskKey();

        String[] taskData = {
                selecteKey,
                task.getTask(),
                task.getDate(),
                task.getState(),
                task.getUserEmail()
        };

        faildTaskUpdate(taskData);
    }

    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

    public float calcPecent (int number, int total){
        return (number/(float) total) * 100;
    }

}