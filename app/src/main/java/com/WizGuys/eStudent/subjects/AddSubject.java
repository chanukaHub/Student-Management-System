package com.WizGuys.eStudent.subjects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.helperClass.Common;
import com.WizGuys.eStudent.model.Subject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubject extends AppCompatActivity {

    TextView subjNameTextView, subjChapterTextView, subjInfoTextView;
    Button addSubjButton;

    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        subjNameTextView = findViewById(R.id.subject_name_add);
        subjChapterTextView = findViewById(R.id.subject_chapters_add);
        subjInfoTextView = findViewById(R.id.subject_info_add);

        addSubjButton.findViewById(R.id.add_subject_Subject_Manager);

        addSubjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subName = subjNameTextView.getText().toString();
                String chapters = subjChapterTextView.getText().toString();
                String subInfo = subjInfoTextView.getText().toString();

                Subject subject = new Subject(subName, chapters, subInfo);

                if (subName.isEmpty() || chapters.isEmpty() || subInfo.isEmpty()){
                    Toast.makeText(AddSubject.this, "Please fill the form.", Toast.LENGTH_LONG).show();
                } else{
                    reff = FirebaseDatabase.getInstance().getReference().child(Common.SUBJECTS_TABLE);
                    reff.push().setValue(subject);
                    Toast.makeText(AddSubject.this, "Subject added successfully.", Toast.LENGTH_LONG).show();



                    //dialog box
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSubject.this);

                    builder.setMessage("Do you want to add another subject?");
                    builder.setTitle("Add another...");
                    //user needs select choice
                    builder.setCancelable(false);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(AddSubject.this, SubjectList.class);
                            startActivity(intent);
                        }
                    });

                    //create alert dialog
                    AlertDialog alertDialog = builder.create();

                    //show alert dialog
                    alertDialog.show();
                }
            }
        });
    }
}