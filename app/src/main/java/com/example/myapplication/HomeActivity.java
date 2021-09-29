package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView1;
    TabLayout tabLayout1;
    ViewPager2 pager2;
    movieDetailsAdapter mAdapter;
    FirebaseFirestore db;
    ArrayList<movieDetailsModel> movieArrayList;
    ProgressDialog progressDialog;
    Button bookNowBtn;
    TextView bookButton;
    String userId;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Movies");
        progressDialog.show();

        recyclerView1 = findViewById(R.id.recyclerView);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        movieArrayList = new ArrayList<movieDetailsModel>();
        mAdapter = new movieDetailsAdapter(HomeActivity.this, movieArrayList);

        recyclerView1.setAdapter(mAdapter);
        showData();
    }

    protected void showData() {
        
        db.collection("MovieDocuments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        movieArrayList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {

                            movieDetailsModel model = new movieDetailsModel(

                                    snapshot.getString("id"),
                                    snapshot.getString("duration"),
                                    snapshot.getString("cat"),
                                    snapshot.getString("name"));
                            movieArrayList.add(model);
                        }
                        mAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Oops... Something went wrong!", Toast.LENGTH_SHORT).show();
            }

        });

    }



}


/*
    public void changeFragment(View view){

        Fragment fragment;

        if(view==findViewById(R.id.cSoon_btn)){
            fragment=new ComingSoon();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.ContView3,fragment);
            ft.commit();
        }

        if(view==findViewById(R.id.nowShowing_btn)){
            fragment=new NowShowing();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.ContView3,fragment);
            ft.commit();
        }
    }*/




