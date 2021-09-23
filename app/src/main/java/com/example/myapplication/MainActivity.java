package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button AdiminBtn;//For rivnduas admin page
    Button homeButton;//For home page of the application, Naveen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdiminBtn=findViewById(R.id.idAdminBtn);///for rivindu admin button
        AdiminBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdmin();
            }
        });

        homeButton=findViewById(R.id.button);
        homeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openHomeActivity();
            }
        });


    }

    public void openHomeActivity(){

        Intent intent=new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    public void openAdmin(){
        Intent intent=new Intent(MainActivity.this,AddmovieActivity.class);
        startActivity(intent);
    }


}