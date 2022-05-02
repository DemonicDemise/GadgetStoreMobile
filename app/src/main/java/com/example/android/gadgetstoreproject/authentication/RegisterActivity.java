package com.example.android.gadgetstoreproject.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.gadgetstoreproject.MainActivity;
import com.example.android.gadgetstoreproject.R;
import com.example.android.gadgetstoreproject.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, alreadyHaveAnAccount;
    private EditText editTextName, editTextEmail, editTextCity, editTextPassword;
    private Button register;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        editTextEmail = findViewById(R.id.inputEmail);
        editTextName = findViewById(R.id.inputName);
        editTextCity = findViewById(R.id.inputCity);
        editTextPassword = findViewById(R.id.inputPassword);

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.setError("Type your name!");
            editTextName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(city.isEmpty()){
            editTextCity.setError("Type your city name!");
            editTextCity.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            UserModel userModel = new UserModel(name, email, city);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users");
                            myRef.child(FirebaseAuth.getInstance().getUid())
                                    .setValue(userModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(),"Registration is successfully done!",
                                            Toast.LENGTH_LONG).show();
                                        }
                                    });
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            // Registration failed
                            Toast.makeText(getApplicationContext(), "Registration failed!!" + " Please try again later",
                                    Toast.LENGTH_LONG).show();
                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
