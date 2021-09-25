package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class AddmovieActivity extends AppCompatActivity {



    private EditText movieName,movieCat,movieDuration;
    private Button addMovieBtn,showMoviesBtn,logout;
    private FirebaseFirestore db;
    private String aId,aName,aCat,aDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);

        movieName=(EditText)findViewById(R.id.idMovieNameEdt);
        movieCat=(EditText)findViewById(R.id.idMovieCatEdt);
        movieDuration=(EditText)findViewById(R.id.idMovieDurationtEdt);
        addMovieBtn=findViewById(R.id.idtAddmovieBtn);
        showMoviesBtn=findViewById(R.id.idtShowMoviesBtn);

        db=FirebaseFirestore.getInstance();
        logout=findViewById(R.id.ALogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddmovieActivity.this,AdminLoginActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            addMovieBtn.setText("Update");

            aId=bundle.getString("aId");
            aName=bundle.getString("aName");
            aCat=bundle.getString("aCat");
            aDuration=bundle.getString("aDuration");

            movieName.setText(aName);//assign to new feild
            movieCat.setText(aCat);
            movieDuration.setText(aDuration);


        }else {
            addMovieBtn.setText("Add Movie");
        }

        showMoviesBtn.setOnClickListener(new View.OnClickListener() {//onclick view movies
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddmovieActivity.this,ViewMovieActivity.class));
            }
        });

        addMovieBtn.setOnClickListener(new View.OnClickListener() {//Onclick addd movie button
            @Override
            public void onClick(View view) {

                String duration=movieDuration.getText().toString();
                String name=movieName.getText().toString();
                String cat=movieCat.getText().toString();

                 Bundle bundle1=getIntent().getExtras();//check if user wants to update data or add new data
                 if (bundle1 !=null){
                     String id=aId;
                     updatetoFirestore(id,name,cat,duration);

                 }else {
                     String id= UUID.randomUUID().toString();//generate random variable

                         saveToFireStore(id, name, cat, duration);
                 }
            }
        });
    }
    private void updatetoFirestore(String id,String name,String cat,String duration){
        if(movieName.length()>=2 && movieCat.length()>=4 && movieDuration.length()>0) {
            float division=Integer.parseInt(duration);
            float s=division/60;
            String du=String.valueOf(s);
            db.collection("MovieDocuments").document(id).update("name", name, "cat", cat, "duration", du)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddmovieActivity.this, "Data Successfully updated", Toast.LENGTH_SHORT).show();
                                movieName.setText("");
                                movieCat.setText("");
                                movieDuration.setText("");
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
        }else{ if(movieName.length()<2){movieName.setError("Movie name >2 characters");}
            if(movieCat.length()<4){movieCat.setError("Movie cat >4");}
            if(movieDuration.length()<=0){movieDuration.setError("Required");}
        }

    }

    private void saveToFireStore(String id,String name,String cat,String duration){///to save to firestore

    if(movieName.length()>=2 && movieCat.length()>=4 && movieDuration.length()>0) {
        float division=Integer.parseInt(duration);
        float s=division/60;
        String du=String.valueOf(s);
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("cat", cat);
            map.put("duration", du);

            db.collection("MovieDocuments").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddmovieActivity.this, "Movie Added", Toast.LENGTH_SHORT).show();
                                movieName.setText("");
                                movieCat.setText("");
                                movieDuration.setText("");
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