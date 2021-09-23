package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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



    private EditText movieName,movieCat,movieDuration,movieImg;
    private Button addMovieBtn,showMoviesBtn;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);

        movieName=findViewById(R.id.idMovieNameEdt);
        movieCat=findViewById(R.id.idMovieCatEdt);
        movieDuration=findViewById(R.id.idMovieDurationtEdt);
        movieImg=findViewById(R.id.idMovieImagetEdt);
        addMovieBtn=findViewById(R.id.idtAddmovieBtn);
        showMoviesBtn=findViewById(R.id.idtShowMoviesBtn);

        db=FirebaseFirestore.getInstance();

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=movieName.getText().toString();
                String cat=movieCat.getText().toString();
                String duration=movieDuration.getText().toString();
                String img=movieImg.getText().toString();
                String id= UUID.randomUUID().toString();

                saveToFireStore(id,name,cat,duration,img);

            }
        });
    }
    private void saveToFireStore(String id,String name,String cat,String duration,String img){
        if(!name.isEmpty() && !cat.isEmpty() && !duration.isEmpty() && !img.isEmpty()){
            HashMap<String,Object> map=new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("cat", cat);
            map.put("duration", duration);
            map.put("img", img);

            db.collection("MovieDocuments").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AddmovieActivity.this, "Movie Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddmovieActivity.this, "Movie Add failed", Toast.LENGTH_SHORT).show();
                }
            });


        }else
            Toast.makeText(AddmovieActivity.this, "Fields empty", Toast.LENGTH_SHORT).show();
    }
}