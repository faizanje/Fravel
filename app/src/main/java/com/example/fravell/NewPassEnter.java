package com.example.fravell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewPassEnter extends AppCompatActivity {

    private Button set_new_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_enter);


        set_new_pass=findViewById(R.id.set_new_pass_btn);
        set_new_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewPassEnter.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}