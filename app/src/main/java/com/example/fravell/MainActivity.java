package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fravell.Models.Buyer;
import com.example.fravell.Models.User;
import com.example.fravell.Utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView editName, editEmail, editPassword, editConfirmPassword, editLogin;
    ProgressBar prBar;
    private Button signup;
    private TextView to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.enterUsername);
        editEmail = findViewById(R.id.enterEmail);
        editPassword = findViewById(R.id.enterPassword);
        //editConfirmPassword = findViewById(R.id.suConfirmPassword);
        //editImage = findViewById(R.id.buyer_image);


        prBar = findViewById(R.id.Spr_bar);

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        to_login = findViewById(R.id.take_to_login);
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });



/*
        signup = findViewById(R.id.signup);
        to_login = findViewById(R.id.take_to_login);

        signup.setOnClickListener(this);
        to_login.setOnClickListener(this);

 */
    }







/*
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                signUp();
                break;

            case R.id.take_to_login:
                startActivity(new Intent(this, Login.class));
                finish();
        }

    }

*/

    private void signUp() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        //String confirmPassword = editConfirmPassword.getText().toString().trim();


        if (name.isEmpty()) {
            editEmail.setError("Full Name is Required!");
            editEmail.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email Address is Required!");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is Required!");
            editPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please provide valid email!");
            editEmail.requestFocus();
            return;
        }


        if (password.length() < 6) {
            editPassword.setError("Password length must be greater than 6");
            editPassword.requestFocus();
            return;
        }


        prBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Buyer buyer = new Buyer(name, email, password);
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myReference = firebaseDatabase.getReference("buyers/");
                            myReference.child(FirebaseAuth.getInstance().getUid()).setValue(buyer);

                            User user = new User(email, password);
                            DBUtils.saveLoggedInUser(buyer);
                            startActivity(new Intent(MainActivity.this, HomeScreen.class));
                            finish();
                            /*FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "Coming till here", Toast.LENGTH_LONG).show();

                                    if(task.isSuccessful()){
                                        prBar.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        prBar.setVisibility(View.GONE);
                                        startActivity(new Intent(MainActivity.this, ProductDetailPage.class));

                                    }
                                    else{
                                        //Toast.makeText(buyer_signup.this, -5.+"Failed to registered. Try Again!", Toast.LENGTH_LONG).show();
                                        prBar.setVisibility(View.GONE);
                                    }
                                }


                            });

                             */
                        } else {
                            //  Toast.makeText(buyer_signup.this, -5.+"Failed to registered. Try Again!", Toast.LENGTH_LONG).show();
                            prBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });



























/*

        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,EmailVeri.class);
                startActivity(intent);
            }
        });
        to_login=findViewById(R.id.take_to_login);
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });



 */

    }
/*
    @Override
    public void onClick(View view) {
        if(view == signup)
            signUp();
        if(view == to_login)
            startActivity(new Intent(this,Login.class));

    }

 */
}