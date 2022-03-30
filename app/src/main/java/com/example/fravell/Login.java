package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    TextView addEmail, addPassword;
    private Button signin;
    private TextView forgotpass, take_to_signup;
    ProgressBar prBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
// User is signed in
            startActivity(new Intent(Login.this, HomeScreen.class));
            finish();
        }

        addEmail = findViewById(R.id.Email);
        addPassword = findViewById(R.id.Password);


        take_to_signup = findViewById(R.id.take_to_signup);
        take_to_signup.setOnClickListener(this);

        signin = findViewById(R.id.signin);
        signin.setOnClickListener(this);

        prBar = findViewById(R.id.Pr_Bar);

        forgotpass = findViewById(R.id.take_to_forgot);
        forgotpass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                loginPage();
                break;

            case R.id.take_to_signup:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.take_to_forgot:
                startActivity(new Intent(this, ForgotPass.class));
                break;
        }

    }

    private void loginPage() {

        String email = addEmail.getText().toString().trim();
        String password = addPassword.getText().toString().trim();

        if (email.isEmpty()) {
            addEmail.setError("Email Address is Required!");
            addEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            addPassword.setError("Password is Required!");
            addPassword.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            addEmail.setError("Please provide valid email!");
            addEmail.requestFocus();
            return;
        }


        if (password.length() < 6) {
            addPassword.setError("Password length must be greater than 6");
            addPassword.requestFocus();
            return;
        }
        prBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    //redirect to the buyer home
                    Log.i("Qasim", "onComplete: I am in Login ");
                    Toast.makeText(Login.this, "You are already logged in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, HomeScreen.class));
                    finish();

                } else {
                    Toast.makeText(Login.this, "Failed to login. Please check your credentials", Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}















