package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {
    EditText AdminUser,AdminPass;
    Button Adminloginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        AdminUser=(EditText)findViewById(R.id.adminName);
        AdminPass=(EditText) findViewById(R.id.adminpass);
        Adminloginbtn=(Button)findViewById(R.id.AloginBtn);

        Adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AdminUser.getText().toString().equals("admin") && AdminPass.getText().toString().equals("admin")){
                    Toast.makeText(AdminLoginActivity.this, "Admin Login succesfull", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(AdminLoginActivity.this,AddmovieActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AdminLoginActivity.this, "Invalid Admin login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}