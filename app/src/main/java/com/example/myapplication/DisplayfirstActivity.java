package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayfirstActivity extends AppCompatActivity {

    private Button userbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayfirst);

        userbtn = findViewById(R.id.button);
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    openuser();
            }

        });



    }
    public void openuser(){
        Intent intent = new Intent(DisplayfirstActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}