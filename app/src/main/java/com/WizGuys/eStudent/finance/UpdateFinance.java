package com.WizGuys.eStudent.finance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.adapter.FinanceAdapter;
import com.WizGuys.eStudent.model.Finance;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


public class UpdateFinance extends AppCompatActivity  {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button chooseImageBtn;
    private FinanceAdapter.OnItemClickListener mListener;
    private Button uploadBtn;
    private EditText finName, finEmail, finAmountReceived,
            finBalance, finBalanceToPayDate,
            date, finDescription;

    private ProgressBar uploadProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    //////////////////////////////
    private FirebaseStorage mStorage;

    TextView nameDetailTextView,descriptionDetailTextView;
    ImageView teacherDetailImageView;
    private void initializeWidgets(){
        nameDetailTextView= findViewById(R.id.nameEditText);
        descriptionDetailTextView= findViewById(R.id.techDescription);
        teacherDetailImageView=findViewById(R.id.chosenImageView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        initializeWidgets();
        /////////////////////////////////////

        mStorage = FirebaseStorage.getInstance();
        uploadBtn = findViewById(R.id.uploadBtnFin);
        finName = findViewById(R.id.finName);
        finEmail = findViewById ( R.id.finEmail );
        finAmountReceived = findViewById ( R.id.finAmountReceived );
        finBalance = findViewById(R.id.finBalance);
        finBalanceToPayDate = findViewById(R.id.finBalanceToPayDate);
        date = findViewById(R.id.findate);
        finDescription = findViewById ( R.id.finDescription );
        /*ED*/

        uploadProgressBar = findViewById(R.id.progress_bar);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("finance_uploads");
        ////////////////////
        Intent i=this.getIntent();
        String id=i.getExtras().getString("ID_KEY");
        String name=i.getExtras().getString("NAME_KEY");
        String email=i.getExtras().getString("EMAIL_KEY");
        String amount=i.getExtras().getString("AMOUNT_KEY");
        String balance=i.getExtras().getString("BALANCE_KEY");
        String duDate=i.getExtras().getString("DU_DATE_KEY");
        String dateval=i.getExtras().getString("DATE_KEY");
        String description=i.getExtras().getString("DESCRIPTION_KEY");



        finName.setText(name);
        finEmail.setText(email);
        finAmountReceived.setText(amount);
        finBalance.setText(balance);
        finBalanceToPayDate.setText(duDate);
        date.setText(dateval);
        finDescription.setText(description);


        final String selectedKey  = id;


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UpdateFinance.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                } else {
                    updateUploadFile(selectedKey);
                }
            }
        });
    }


    private void updateUploadFile(final String selectedKey) {


            uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgressBar.setVisibility(View.VISIBLE);
                                    uploadProgressBar.setIndeterminate(false);
                                    uploadProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(UpdateFinance.this, "Finance Update successful", Toast.LENGTH_LONG).show();


                            Finance upload = new Finance(finName.getText().toString().trim(),

                                    finEmail.getText ().toString (),
                                    finAmountReceived.getText().toString(),
                                    finBalance.getText().toString(),
                                    finBalanceToPayDate.getText().toString(),
                                    date.getText().toString(),
                                    finDescription.getText().toString());
                            String uploadId = selectedKey;
                            mDatabaseRef.child(uploadId).setValue(upload);
                            uploadProgressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }




}