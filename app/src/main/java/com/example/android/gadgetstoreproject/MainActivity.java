package com.example.android.gadgetstoreproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.android.gadgetstoreproject.authentication.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar progressBar;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = findViewById(R.id.signInBtn);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInBtn:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }
}