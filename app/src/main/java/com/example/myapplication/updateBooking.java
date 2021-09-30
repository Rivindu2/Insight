package com.example.myapplication;

import static com.example.myapplication.bookUpdateUtils.CHANNEL_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class updateBooking extends AppCompatActivity {

    private FirebaseFirestore db;
    private String  uId;
    TextView mTitle;
    TextView seatsNo,ticketId, dash;
    EditText eDate;
    Button updateBtn, updateBackBtn;
    String userId;
    private FirebaseAuth fAuth;
    String uTitle, uDate, seatsNum;
    ProgressDialog progressDialog;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_booking);
        dash=findViewById(R.id.tv_topic2);

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(updateBooking.this,dashboardActivity.class);
                startActivity(intent);

            }
        });
        mTitle=findViewById(R.id.mName);
        eDate=findViewById(R.id.et_date);
        seatsNo=findViewById(R.id.seatsNo);
        updateBtn=findViewById(R.id.updateBtn);
        ticketId=findViewById(R.id.ticketId);


        Calendar calender=Calendar.getInstance();
        final int year=calender.get(calender.YEAR);
        final int month=calender.get(calender.MONTH);
        final int day=calender.get(calender.DATE);


        progressDialog = new ProgressDialog(this);
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        updateBooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        eDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        db=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){

            uTitle=bundle.getString("mTitle");
            uDate=bundle.getString("eDate");
            seatsNum=bundle.getString("seatsNum");
            uId=bundle.getString("id");

            mTitle.setText(uTitle);
            eDate.setText(uDate);
            seatsNo.setText(seatsNum);
            ticketId.setText(uId);

        }else{
            Toast.makeText(updateBooking.this,"Oops... Something went wrong please contact support...",Toast.LENGTH_SHORT).show();

        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Updating");
                progressDialog.show();
                String date=eDate.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                String id=uId;

                updateToFireStore(id, date);
            }
        });
        updateBackBtn=findViewById(R.id.updateBackBtn);
        updateBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBookDetails();
            }
        });

    }

    public void viewBookDetails(){
        Intent intent = new Intent(this, BookDetails.class);
        startActivity(intent);
    }


    public void headsUpNotification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this, CHANNEL_ID )
                .setSmallIcon(R.drawable.movie)
                .setContentTitle(bookUpdateUtils.Noti_Title)
                .setContentText(bookUpdateUtils.Noti_Desc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(bookUpdateUtils.Noti_Id, builder.build());
    }

    private void updateToFireStore(String id, String date){
        userId=fAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("MovieBookings").document(id)
                .update("bookDate", date).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(task.isSuccessful()){
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    headsUpNotification();
                    Toast.makeText(updateBooking.this,"Date has been update...",Toast.LENGTH_SHORT).show();

                }else{
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(updateBooking.this,"Error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(updateBooking.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}