package com.WizGuys.eStudent.finance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.WizGuys.eStudent.R;
import com.WizGuys.eStudent.teachers.TeachersDashboard;
import com.WizGuys.eStudent.teachers.Upload;

public class FinanceDashboard extends AppCompatActivity {

    private ImageButton addFinancePayment,allFinance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_dashboard);

        addFinancePayment = findViewById(R.id.addNewPayment);
        allFinance = findViewById(R.id.allPayments);

        addFinancePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinanceDashboard.this, UploadFinance.class);
                startActivity(i);
            }
        });
        allFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinanceDashboard.this, AllFinance.class);
                startActivity(i);
            }
        });
    }
}