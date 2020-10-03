package com.WizGuys.eStudent.todoList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.WizGuys.eStudent.model.Task;

public class UpdateToDo extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImageBtn;
    private Button uploadBtn;
    private EditText taskData, taskDate;
    private ImageView chosenImageView;
    private ProgressBar uploadProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    //////////////////////////////
    private FirebaseStorage mStorage;

    TextView task_data,task_date;

    private void initializeWidgets(){
        task_data = findViewById(R.id.task_data_ToDo_add);
        task_date= findViewById(R.id.task_date);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);
        initializeWidgets();
        /////////////////////////////////////

        mStorage = FirebaseStorage.getInstance();

        uploadBtn = findViewById(R.id.add_task_img_ToDo);

        //ET Text
        taskData = findViewById(R.id.task_data_ToDo_add);
        taskDate = findViewById ( R.id.task_date);

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Task");
        ////////////////////
        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        String task=i.getExtras().getString("NAME_KEY");
        String dateToDo=i.getExtras().getString("DATE_KEY");
        String state=i.getExtras().getString("STATE_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");


        taskData.setText(task);
        taskDate.setText(dateToDo);


        final String selectedKey  = id;

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateToDo.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    updateUploadFile(selectedKey);
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

    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, TeachersDashboard.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(chosenImageView);
        }
    }
}