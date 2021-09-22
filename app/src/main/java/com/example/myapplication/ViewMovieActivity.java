package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewMovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdminAdapter adapter;
    private List<AdminModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);

        recyclerView=findViewById(R.id.mrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        adapter=new MyAdminAdapter(this,list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper= new ItemTouchHelper(new TouchSlide(adapter));//to move movie cards left and right
        touchHelper.attachToRecyclerView(recyclerView);

        showData();//to fetch data
    }
    public void  showData(){//fetch data for instance of collection
        db.collection("MovieDocuments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                for (DocumentSnapshot snapshot:task.getResult()){

                    AdminModel model=new AdminModel(snapshot.getString("id"),snapshot.getString("name"),snapshot.getString("cat"),snapshot.getString("duration"));
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewMovieActivity.this, "Error in retrieving", Toast.LENGTH_SHORT).show();
            }
        });
    }
}