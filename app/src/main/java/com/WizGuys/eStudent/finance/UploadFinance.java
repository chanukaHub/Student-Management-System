package com.WizGuys.eStudent.finance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.model.Finance;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;


public class UploadFinance extends AppCompatActivity {

    private Button uploadBtnFin;
    private EditText finName, finEmail, finAmountReceived,
            finBalance, finBalanceToPayDate,
            date, finDescription;

    private DatabaseReference mDatabaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_payment );

        uploadBtnFin = findViewById(R.id.uploadBtnFin);

        finName = findViewById(R.id.finName);
        finEmail = findViewById ( R.id.finEmail );
        finAmountReceived = findViewById ( R.id.finAmountReceived );
        finBalance = findViewById(R.id.finBalance);
        finBalanceToPayDate = findViewById(R.id.finBalanceToPayDate);
        date = findViewById(R.id.findate);
        finDescription = findViewById ( R.id.finDescription );


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("finance_uploads");

        uploadBtnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadFile();
            }
        });
    }


    private void uploadFile() {

        Finance upload = new Finance(
                finName.getText().toString().trim(),
                finEmail.getText().toString(),
                finAmountReceived.getText().toString(),
                finBalance.getText().toString(),
                finBalanceToPayDate.getText().toString(),
                date.getText().toString(),
                finDescription.getText().toString()
        );
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);

        Toast.makeText(UploadFinance.this, "Upload Success", Toast.LENGTH_SHORT).show();

        finName.setText("");
        finEmail.setText("");
        finAmountReceived.setText("");
        finBalance.setText("");
        finBalanceToPayDate.setText("");
        date.setText("");
        finDescription.setText("");
    }

}