package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Cusbooking extends AppCompatActivity {
    private TextInputEditText Mid , Mname , Noofseats , Showdate;
    private TextView Tprice;
    private Button EBtn , EVBtn;
    private FirebaseFirestore db;
    private String uMid, uMname, uNoofseats, uShowdate, uTprice, uId;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusbooking);

        Mid=findViewById(R.id.textView20);
        Mname=findViewById(R.id.textView21);
        Noofseats=findViewById(R.id.textView22);
        Showdate=findViewById(R.id.textView23);
        Tprice=findViewById(R.id.textView24);
        EBtn=findViewById(R.id.btn10);
        EVBtn=findViewById(R.id.button3);


        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
             EBtn.setText("Update Booking");
             uMid = bundle.getString("uMid");
             uId = bundle.getString("uId");
             uMname = bundle.getString("uMname");
             uNoofseats = bundle.getString("uNoofseats");
             uShowdate = bundle.getString("uShowdate");
             uTprice = bundle.getString("uTprice");
             Mid.setText(uMid);
             Mname.setText(uMname);
             Noofseats.setText(uNoofseats);
             Showdate.setText(uShowdate);
             Tprice.setText(uTprice);
        }else {
            EBtn.setText("Save");
        }

        EVBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cusbooking.this , ShowbookingActivity.class));
            }
        });

        EBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float addition=Integer.parseInt(Noofseats.getText().toString());
                String tprice = String.valueOf(addition * 500);
                String mid = Mid.getText().toString();
                String mname = Mname.getText().toString();
                String noofseats = Noofseats.getText().toString();
                String showdate = Showdate.getText().toString();


                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , mid , mname , noofseats , showdate , tprice);

                }else{
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id , mid , mname , noofseats , showdate , tprice);
                }

            }
        });

    }

    private void updateToFireStore(String id, String mid, String mname, String noofseats, String showdate, String tprice){
        userId = fAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("BookingInfo").document(id).update("mid" , mid , "mname" , mname , "noofseats" , noofseats , "showdate" , showdate , "tprice" , tprice)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Cusbooking.this, "Data Updated!!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Cusbooking.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cusbooking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String mid, String mname, String noofseats, String showdate, String tprice) {
        if (!mid.isEmpty() && !mname.isEmpty() && !noofseats.isEmpty() && !showdate.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("mid" , mid);
            map.put("mname" , mname);
            map.put("noofseats" , noofseats);
            map.put("showdate" , showdate);
            map.put("tprice" , tprice);

            userId = fAuth.getCurrentUser().getUid();

            db.collection("users").document(userId).collection("BookingInfo").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Cusbooking.this, "Data Saved!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cusbooking.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}