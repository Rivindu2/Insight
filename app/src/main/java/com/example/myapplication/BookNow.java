package com.example.myapplication;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class BookNow extends AppCompatActivity {

    TextView name, cat, duration, tvDate;
    EditText etDate, getDate, seatNo;
    DatePickerDialog.OnDateSetListener setListener;
    movieDetailsAdapter mAdapter;
    ProgressDialog progressDialog;
    Button bookBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        name=findViewById(R.id.title);
        cat=findViewById(R.id.genre);
        duration=findViewById(R.id.mDuration);
        bookBtn=findViewById(R.id.bookButton);
        getDate=findViewById(R.id.et_date);
        seatNo=findViewById(R.id.noOfSeats);

        Intent i=getIntent();
        String Name=i.getStringExtra("name");
        String Cat=i.getStringExtra("cat");
        String Duration=i.getStringExtra("duration");

        if(Name!=null && Cat!=null && Duration !=null) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            name.setText(Name);
            cat.setText(Cat);
            duration.setText(Duration);
            Context context=getApplicationContext();
            CharSequence text="Book your movie...";
            int duration=Toast.LENGTH_SHORT;
            Toast toast=Toast.makeText(context, text, duration);

        }else{
            Context context=getApplicationContext();
            CharSequence text="No Movie Data, something went wrong...";
            int duration=Toast.LENGTH_SHORT;
            Toast toast=Toast.makeText(context, text, duration);
        }
//
//        tvDate=findViewById(R.id.tv_date);
        etDate=findViewById(R.id.et_date);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

//        tvDate.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                DatePickerDialog datePickerDialog=new DatePickerDialog(
//                        BookNow.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        (DatePickerDialog.OnDateSetListener) setListener,year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//
//            }
//        });
        setListener=new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                month=month+1;
                String date=day+"/"+month+"/"+year;
                tvDate.setText(date);

            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        BookNow.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = name.getText().toString();
                String date = getDate.getText().toString();
                String seatsNo = seatNo.getText().toString();
                String id = UUID.randomUUID().toString();


                if (isNumber(seatsNo)) {
                    int seatCalc = Integer.parseInt(seatsNo);
                    int Total = seatCalc * 500;
                    saveToFireStore(id, title, date, seatCalc, Total);

                } else {
                    Toast.makeText(BookNow.this, "Only Numbers are allowed!!!", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    public static boolean isNumber(String in){

        try{
            Integer.parseInt(in);
            return true;
        }catch(Exception E){
            return false;
        }
    }

    private void saveToFireStore(String id, String mName, String bookDate, int seatNo, int Total){
        if(!mName.isEmpty() && !bookDate.isEmpty() && seatNo!=0 && Total!=0){

            HashMap<String , Object>map=new HashMap<>();
            map.put("id", id);
            map.put("movieName", mName);
            map.put("bookDate", bookDate);
            map.put("No_Of_Seats", seatNo);
            map.put("Total", Total);

            userId=fAuth.getCurrentUser().getUid();

            fStore.collection("users").document(userId).collection("MovieBookings")
                    .document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(BookNow.this, "Booking Completed!!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(BookNow.this, "Oops... Booking Failed something went wrong!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            Toast.makeText(this,"PlEASE SELECT BOOKING DETAILS PROPERLY... ", Toast.LENGTH_LONG).show();
        }
    }


}