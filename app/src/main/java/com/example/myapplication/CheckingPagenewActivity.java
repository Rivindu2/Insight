package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.UUID;

public class CheckingPagenewActivity extends AppCompatActivity {
    TextView Id , MovieName , NoOfseats , bookDate , Total;
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

    //dialog variables
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;

    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_pagenew);


        btn_success = (Button) findViewById(R.id.btn_paynow);
        dash=findViewById(R.id.tv_topic);
        back=findViewById(R.id.textView33);

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckingPagenewActivity.this,dashboardActivity.class);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckingPagenewActivity.this,ViewCardActivity.class);
                startActivity(intent);

            }
        });

        Input1 = findViewById(R.id.textView27);
        Input2 = findViewById(R.id.textView28);
        Input3 = findViewById(R.id.textView29);
        Input4 = findViewById(R.id.textView30);

        Intent i = getIntent();
        String input1 = i.getStringExtra("Input1");
        String input2 = i.getStringExtra("Input2");
        String input3 = i.getStringExtra("Input3");
        String input4 = i.getStringExtra("Input4");

        if (input1!=null && input2!=null && input3!=null && input4!=null){
            Input1.setText(input1);
            Input2.setText(input2);
            Input3.setText(input3);
            Input4.setText(input4);
            Context context = getApplicationContext();



        }else {

        }

        /*Id = findViewById(R.id.textView16);
        MovieName = findViewById(R.id.textView17);
        bookDate = findViewById(R.id.textView18);
        NoOfseats = findViewById(R.id.textView25);
        Total = findViewById(R.id.textView31);

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

        }*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(R.layout.my_success_dialog);

            }
        });

        /*fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        String id = UUID.randomUUID().toString();

        documentReference = fStore.collection("users").document(userId).collection("BookingInfo").document("f3a16db8-056e-4e62-af1d-42816fe5261f");
        documentReference.addSnapshotListener(CheckingPagenewActivity.this, new EventListener<DocumentSnapshot>() {
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
        });*/

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

                //notification code goes here
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CheckingPagenewActivity.this, "My Notification");
                builder.setContentTitle("PAYMENT NOTIFICATION");
                builder.setContentText("Payment Successfully!!");
//                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CheckingPagenewActivity.this);
                managerCompat.notify(1,builder.build());

                startActivity(new Intent(CheckingPagenewActivity.this , dashboardActivity.class));
            }
        });
    }
}