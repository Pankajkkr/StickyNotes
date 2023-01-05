package com.rvgroup.stickynotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rvgroup.stickynotes.MainActivity;
import com.rvgroup.stickynotes.R;
import com.rvgroup.stickynotes.databinding.ActivityDataInsertBinding;

public class DataInsertActivity extends AppCompatActivity {

    ActivityDataInsertBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String type = getIntent().getStringExtra("type");

        if (type.equals("update")) {
            setTitle("update");
            binding.titlepost.setText(getIntent().getStringExtra("title"));
            binding.bodypost.setText(getIntent().getStringExtra("body"));
            int id = getIntent().getIntExtra("id",0);
            binding.postbutton.setText("Update Note");
            binding.postbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.titlepost.length() == 0) {
                        binding.titlepost.setError("plz required");
                    } else if (binding.bodypost.length() == 0) {
                        binding.bodypost.setError("plz enter post");
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("title", binding.titlepost.getText().toString());
                        intent.putExtra("body", binding.bodypost.getText().toString());
                        intent.putExtra("id", id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            } else {
            setTitle("ADD Mode");
            binding.postbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.titlepost.length() == 0) {
                        binding.titlepost.setError("plz required");
                    } else if (binding.bodypost.length() == 0) {
                        binding.bodypost.setError("plz enter post");
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("title", binding.titlepost.getText().toString());
                        intent.putExtra("body", binding.bodypost.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this, MainActivity.class));
    }
}