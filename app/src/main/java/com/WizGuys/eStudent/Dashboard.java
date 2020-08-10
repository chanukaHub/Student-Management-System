package com.WizGuys.eStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bLogin:

                break;
                case  R.id.tvRegisterLink:
                startActivity(new Intent(this,Register.class));
                break;


        }
    }
}