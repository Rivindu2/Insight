package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class dashboardActivity extends AppCompatActivity {

    Button resendCode;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TextView textView,verifyMsg,textView2,feedbackView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView=(TextView)findViewById(R.id.textView7);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dashboardActivity.this,Cusbooking.class);
                startActivity(intent);

            }
        });

        textView=(TextView)findViewById(R.id.textView10);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dashboardActivity.this,UserprofileActivity.class);
                startActivity(intent);

            }
        });
        feedbackView=(TextView)findViewById(R.id.textView8);

        feedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(dashboardActivity.this,feedbackActivity.class);
                startActivity(intent);

            }
        });






        textView=(TextView)findViewById(R.id.textView9);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//select pay activity
                Intent intent=new Intent(dashboardActivity.this,selectPayActivity.class);
                startActivity(intent);

            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        resendCode = findViewById(R.id.resendCode);
        verifyMsg = findViewById(R.id.verify);

        final FirebaseUser user = fAuth.getCurrentUser();

        assert user != null;
        if (!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());

                        }
                    });


                }
            });
        }
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}