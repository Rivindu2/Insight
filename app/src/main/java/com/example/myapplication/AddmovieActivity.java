package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AddmovieActivity extends AppCompatActivity {
    Context context;

    private EditText movieName,movieCat,movieDuration;
    private Button addMovieBtn,showMoviesBtn,logout;
    private FirebaseFirestore db;
    private String aId,aName,aCat,aDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);
        //Notification
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){//notification for chanel////for notification
            NotificationChannel channel= new NotificationChannel("AdminNotification","AdminNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }//check for notifications
        //check internet connection on home page
        if(!isConnected()){
            Toast.makeText(AddmovieActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(AddmovieActivity.this, "welcome to app", Toast.LENGTH_SHORT).show();
        }



        movieName=(EditText)findViewById(R.id.idMovieNameEdt);
        movieCat=(EditText)findViewById(R.id.idMovieCatEdt);
        movieDuration=(EditText)findViewById(R.id.idMovieDurationtEdt);
        addMovieBtn=findViewById(R.id.idtAddmovieBtn);
        showMoviesBtn=findViewById(R.id.idtShowMoviesBtn);

        db=FirebaseFirestore.getInstance();
        logout=findViewById(R.id.ALogout);
        logout.setOnClickListener(new View.OnClickListener() {//log out button
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddmovieActivity.this,AdminLoginActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){//to check if to add movie or update
            addMovieBtn.setText("Update");

            aId=bundle.getString("aId");
            aName=bundle.getString("aName");
            aCat=bundle.getString("aCat");
            aDuration=bundle.getString("aDuration");

            movieName.setText(aName);//assign to new feild
            movieCat.setText(aCat);
            movieDuration.setText(aDuration);


        }else {//to add movie
            addMovieBtn.setText("Add Movie");
        }

        showMoviesBtn.setOnClickListener(new View.OnClickListener() {//onclick view movies
            @Override
            public void onClick(View view) {
                if(!isConnected()){//check internet connection for view movies
                    Toast.makeText(AddmovieActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(AddmovieActivity.this, ViewMovieActivity.class));
                }
            }
        });

        addMovieBtn.setOnClickListener(new View.OnClickListener() {//Onclick addd movie button
            @Override
            public void onClick(View view) {//

                String duration=movieDuration.getText().toString();
                String name=movieName.getText().toString();
                String cat=movieCat.getText().toString();

                 Bundle bundle1=getIntent().getExtras();//check if user wants to update data or add new data
                 if (bundle1 !=null){
                     if(!isConnected()){//check internet connection for add
                         Toast.makeText(AddmovieActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                     }else {
                         String id = aId;
                         updatetoFirestore(id, name, cat, duration);
                     }
                 }else {//check internet connection for update
                     if(!isConnected()){
                         Toast.makeText(AddmovieActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                     }else {
                         String id = UUID.randomUUID().toString();//generate random variable
                         saveToFireStore(id, name, cat, duration);
                     }
                 }
            }
        });
    }
    private void updatetoFirestore(String id,String name,String cat,String duration){//update to firestore method
        if(movieName.length()>=2 && movieCat.length()>=4 && movieDuration.length()>0) {
            float division=Float.parseFloat(duration); //calculation
            float s=division/60;
            String du=String.valueOf(s);
            db.collection("MovieDocuments").document(id).update("name", name, "cat", cat, "duration", du)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddmovieActivity.this, "Data Successfully updated", Toast.LENGTH_SHORT).show();
                                movieName.setText("");//reset fields
                                movieCat.setText("");
                                movieDuration.setText("");
                                NotificationCompat.Builder builder=new NotificationCompat.Builder(AddmovieActivity.this,"AdminNotification");
                                builder.setContentTitle("Insight Admin Movie Updated");
                                builder.setContentText(name);
                                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                                builder.setAutoCancel(true);

                                NotificationManagerCompat managerCompat=NotificationManagerCompat.from(AddmovieActivity.this);
                                managerCompat.notify(1,builder.build());
                            } else {
                                Toast.makeText(AddmovieActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddmovieActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{ if(movieName.length()<2){movieName.setError("Movie name >2 characters");}//update validaton
            if(movieCat.length()<4){movieCat.setError("Movie cat >4");}//update validation
            if(movieDuration.length()<=0){movieDuration.setError("Required");}//update validation
        }

    }
    private void saveToFireStore(String id,String name,String cat,String duration){///to save to firestore method

    if(movieName.length()>=2 && movieCat.length()>=4 && movieDuration.length()>0) {
        float division=Float.parseFloat(duration);//calculation
        float s=division/60;
        String du=String.valueOf(s);
            HashMap<String, Object> map = new HashMap<>();//hashput to save(put) new to firestore
            map.put("id", id);
            map.put("name", name);
            map.put("cat", cat);
            map.put("duration", du);

            db.collection("MovieDocuments").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {//add to firestore
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddmovieActivity.this, "Movie Added", Toast.LENGTH_SHORT).show();
                                movieName.setText("");
                                movieCat.setText("");
                                movieDuration.setText("");
                                NotificationCompat.Builder builder=new NotificationCompat.Builder(AddmovieActivity.this,"AdminNotification");//notification
                                builder.setContentTitle("Insight Admin Movie Added");
                                builder.setContentText(name);
                                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                                builder.setAutoCancel(true);

                                NotificationManagerCompat managerCompat=NotificationManagerCompat.from(AddmovieActivity.this);
                                managerCompat.notify(1,builder.build());

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddmovieActivity.this, "Movie Add failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else{ if(movieName.length()<2){movieName.setError("Movie name >2 characters");}
                if(movieCat.length()<4){movieCat.setError("Movie cat >4");}
                 if(movieDuration.length()<=0){movieDuration.setError("Required");}
            }

    }
    private boolean isConnected(){//check internet connection method
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}





/*  float division=Integer.parseInt(movieDuration.getText().toString());
                float s=division/60;
                String duration=String.valueOf(s);*/


/*else if(movieName.length()<2){
                        movieName.setError("Movie name >2");
                        if(movieCat.length()<4){movieCat.setError("Movie cat >4");}
                }
                else if(movieCat.length()<4){
                     movieCat.setError("Movie cat >4");
                        if(movieName.length()<2){movieName.setError("Movie name >2");}
                }
                else if(movieDuration.length()<2){
                                 movieDuration.setError("duration>60");
                                 if(movieCat.length()<4){movieCat.setError("Movie cat >4");}
                                 if(movieName.length()<2){movieName.setError("Movie name >2");}
                            }*/