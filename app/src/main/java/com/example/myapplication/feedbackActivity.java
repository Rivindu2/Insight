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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mName=findViewById(R.id.plain_1);
        mFeedback=findViewById(R.id.plain_2);
        mSavefeed=findViewById(R.id.save_btn);
        mShowfeed=findViewById(R.id.showfeed_btn);

        db = FirebaseFirestore.getInstance();

        mSavefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString();
                String Feedback = mFeedback.getText().toString();
                String id = UUID.randomUUID().toString();

                saveToFireStore(id, Name, Feedback);
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




   /* private EditText mName, mFeedback;
    private Button mSavefeed,mShowfeed;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mName = findViewById(R.id.plain_1);
        mFeedback = findViewById(R.id.plain_2);
        mSavefeed = findViewById(R.id.save_btn);
        mShowfeed = findViewById(R.id.showfeed_btn);

        db = FirebaseFirestore.getInstance();


        mSavefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString();
                String Feedback = mFeedback.getText().toString();
                String id = UUID.randomUUID().toString();

                saveToFireStore(id, Name, Feedback);
            }
        });

    }

    private void saveToFireStore(String id, String Name, String Feedback) {

        if (!Name.isEmpty() && !Feedback.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("Name", Name);
            map.put("Feedback", Feedback);

            db.collection("feedbackDetails").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(feedbackActivity.this, "Feedback saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                    
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(feedbackActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });
                        
            
        }else
            Toast.makeText(this, "Empty fields not Allowed", Toast.LENGTH_SHORT).show();
    }*/

}