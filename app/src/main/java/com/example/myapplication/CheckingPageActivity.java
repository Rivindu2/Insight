package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private FirebaseFirestore db;
    private List<CardModel> list;
    Button btn_success;

    //dialog variables
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

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
        btn_success = (Button) findViewById(R.id.btn_paynow);

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(R.layout.my_success_dialog);

            }
        });



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        String id = UUID.randomUUID().toString();








       documentReference = fStore.collection("users").document(userId).collection("BookingInfo").document("f3a16db8-056e-4e62-af1d-42816fe5261f");
        documentReference.addSnapshotListener(CheckingPageActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    Mid.setText(documentSnapshot.getString("mid"));
                    Mname.setText(documentSnapshot.getString("mname"));
                    Noofseats.setText(documentSnapshot.getString("noofseats"));
                    Showdate.setText(documentSnapshot.getString("showdate"));
                    Tprice.setText(documentSnapshot.getString("tprice"));


                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }

    private void showAlertDialog(int myLayout) {
        builderDialog = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(myLayout, null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.buttonOK);
        builderDialog.setView(layoutView);
        alertDialog = builderDialog.create();
        alertDialog.show();

        //click on OK Button
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dismiss dialog
                alertDialog.dismiss();
                startActivity(new Intent(CheckingPageActivity.this , dashboardActivity.class));
            }
        });
    }


}