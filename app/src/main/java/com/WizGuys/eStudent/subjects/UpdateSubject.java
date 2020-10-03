package com.WizGuys.eStudent.subjects;

import androidx.appcompat.app.AppCompatActivity;

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

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Subject;
import com.WizGuys.eStudent.model.Task;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.WizGuys.eStudent.todoList.UpdateToDo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class UpdateSubject extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImageBtn;
    private Button uploadBtn;
    private EditText subjectNameET, chaptersET, infoET;
    private ImageView chosenImageView;
    private ProgressBar uploadProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    //////////////////////////////
    private FirebaseStorage mStorage;

    TextView subNameTxt, subChapterTxt, subInfoTxt;

    private void initializeWidgets(){
        subNameTxt = findViewById(R.id.subject_name_add);
        subChapterTxt= findViewById(R.id.subject_chapters_add);
        subInfoTxt = findViewById(R.id.subject_info_add);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        initializeWidgets();
        /////////////////////////////////////

        mStorage = FirebaseStorage.getInstance();

        uploadBtn = findViewById(R.id.add_subject_Subject_Manager);

        //ET Text
        subNameTxt = findViewById(R.id.subject_name_add);
        subChapterTxt = findViewById ( R.id.subject_chapters_add);
        subInfoTxt = findViewById(R.id.subject_info_add);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Common.SUBJECTS_TABLE);
        ////////////////////
        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        String nameSub=i.getExtras().getString("NAME_KEY");
        String chapter=i.getExtras().getString("CHAPTER_KEY");
        String info=i.getExtras().getString("INFO_KEY");


        subNameTxt.setText(nameSub);
        subChapterTxt.setText(chapter);
        subInfoTxt.setText(info);


        final String selectedKey  = id;

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateSubject.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    updateUploadFile(selectedKey);
                }
            }
        });
    }


    private void updateUploadFile(final String selectedKey) {
        Intent i=this.getIntent();

        String nameSub1=i.getExtras().getString("STATE_KEY");
        String chapter1=i.getExtras().getString("CHAPTER_KEY");
        String info=i.getExtras().getString("INFO_KEY");

        Subject upload = new Subject(
                subNameTxt.getText().toString().trim(),
                subChapterTxt.getText().toString(),
                subInfoTxt.getText().toString()
        );
        String uploadId = selectedKey;
        mDatabaseRef.child(uploadId).setValue(upload);
        Toast.makeText(UpdateSubject.this, "Update Success", Toast.LENGTH_SHORT).show();

        subNameTxt.setText("");
        subChapterTxt.setText("");
        subInfoTxt.setText("");

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