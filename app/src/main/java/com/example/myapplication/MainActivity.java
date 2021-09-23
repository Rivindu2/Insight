package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button AdiminBtn;//For rivnduas admin page
    private Button userBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userBtn=findViewById(R.id.button);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUser();
            }
        });



        AdiminBtn=findViewById(R.id.idAdminBtn);///for rivindu admin button
        AdiminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdmin();
            }
        });
    }
    public void openAdmin(){
        Intent intent=new Intent(MainActivity.this,AddmovieActivity.class);
        startActivity(intent);
    }

    public void openUser(){
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }


}