package com.rvgroup.stickynotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rvgroup.stickynotes.MainActivity;
import com.rvgroup.stickynotes.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Handler handler = new Handler();
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Checking");
        progressDialog.setMessage("Opening Your Notes");
//        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);

    }
}