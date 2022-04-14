package com.example.fravell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmailVeri extends AppCompatActivity {

    private Button emailverification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_veri);

        emailverification = findViewById(R.id.verification);
        emailverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailVeri.this, PhoneNumVeri.class);
                startActivity(intent);
            }
        });
    }
}