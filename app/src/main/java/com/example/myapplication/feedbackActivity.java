package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class feedbackActivity extends AppCompatActivity {
    private EditText mName,mFeedback;
    private Button mSavefeed,mShowfeed;
    private FirebaseFirestore db;
    private String uID,uName,uFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mName=findViewById(R.id.plain_1);
        mFeedback=findViewById(R.id.plain_2);
        mSavefeed=findViewById(R.id.save_btn);
        mShowfeed=findViewById(R.id.showfeed_btn);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mSavefeed.setText("Update Feedback");
            uID = bundle.getString("uID");
            uName = bundle.getString("uName");
            uFeedback=bundle.getString("uFeedback");
            mName.setText(uName);
            mFeedback.setText(uFeedback);
        }else{
            mSavefeed.setText("Save");
        }

        mShowfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(feedbackActivity.this,showfeedActivity.class));
            }
        });

        mSavefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString();
                String Feedback = mFeedback.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    String id = uID;
                    updateToFireStore(id,Name,Feedback);

                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, Name, Feedback);
                }

            }
        });
    }
    private void updateToFireStore(String id ,String Name ,String Feedback){
        db.collection("Feedback_details").document(id).update("Name",Name,"Feedback",Feedback)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(feedbackActivity.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(feedbackActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(feedbackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id ,String Name, String Feedback) {
        if (!Name.isEmpty() && !Feedback.isEmpty() ){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("Name" , Name);
            map.put("Feedback" , Feedback);


            db.collection("Feedback_details").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(feedbackActivity.this, "Data Saved!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(feedbackActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();

    }





}