package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class CheckingPageActivity extends AppCompatActivity {
    TextView Mid , Mname , Noofseats , Showdate , Tprice;
    TextView fullName,email,phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String bid;
    FirebaseUser user;

    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_page);
        Mid = findViewById(R.id.Mid);
        Mname = findViewById(R.id.Mname);
        Noofseats = findViewById(R.id.Noofseats);
        Showdate = findViewById(R.id.Showdate);
        Tprice = findViewById(R.id.Tprice);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        String id = UUID.randomUUID().toString();



       documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(CheckingPageActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    Mid.setText(documentSnapshot.getString("Mid"));
                    Mname.setText(documentSnapshot.getString("Mname"));
                    Noofseats.setText(documentSnapshot.getString("Noofseats"));
                    Showdate.setText(documentSnapshot.getString("Showdate"));
                    Tprice.setText(documentSnapshot.getString("Tprice"));


                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }


}