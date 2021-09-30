package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RetrievedetailsActivity extends AppCompatActivity {
    TextView Id , MovieName , NoOfseats , bookDate , Total;
    Button select , backnav;
    TextView fullName,email,phone;
    TextView Input1 , Input2 , Input3 , Input4;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String bid;
    FirebaseUser user;
    private FirebaseFirestore db;
    private List<CardModel> list;
    Button btn_success;
    TextView Tot;
    int Addition = 200;
    Button cal;
    TextView dash , back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievedetails);
        select = findViewById(R.id.button4);
        backnav = findViewById(R.id.button5);
        backnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetrievedetailsActivity.this , BookDetails.class));
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetrievedetailsActivity.this , selectPayActivity.class));
            }
        });


        Id = findViewById(R.id.textView34);
        MovieName = findViewById(R.id.textView35);
        bookDate = findViewById(R.id.textView36);
        NoOfseats = findViewById(R.id.textView37);
        Total = findViewById(R.id.textView38);

        Intent o = getIntent();
        String id = o.getStringExtra("id");
        String movieName = o.getStringExtra("movieName");
        String noOfseats = o.getStringExtra("No_Of_Seats");
        String bookdate = o.getStringExtra("bookDate");
        String total = o.getStringExtra("Total");

        if (id!=null && movieName!=null && bookdate!=null && noOfseats!=null && total!=null){
            Id.setText(id);
            MovieName.setText(movieName);
            bookDate.setText(bookdate);
            NoOfseats.setText(noOfseats);
            Total.setText(total);
            Context context = getApplicationContext();
        }else {

        }


    }
}