package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class BookDetails extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerViewBookings;
    private ArrayList<BookDetailsModel>bookDetailsArrayList;
    private FirebaseAuth fAuth;
    private FirebaseFirestore db;
    private bookDetailsAdapter bAdapter;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Tickets");
        progressDialog.show();

        recyclerViewBookings=findViewById(R.id.recyclerViewBook);
        recyclerViewBookings.setHasFixedSize(true);
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(this));
        fAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        bookDetailsArrayList=new ArrayList<BookDetailsModel>();
        bAdapter=new bookDetailsAdapter(BookDetails.this, bookDetailsArrayList);

        recyclerViewBookings.setAdapter(bAdapter);
        showData();

    }

    public void showData(){

        userId=fAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("MovieBookings").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        bookDetailsArrayList.clear();
                        for(DocumentSnapshot snapshot:task.getResult()){
                            BookDetailsModel model=new BookDetailsModel(
                                    snapshot.getString("id"),
                                    snapshot.getString("movieName"),
                                    snapshot.getDouble("No_Of_Seats"),
                                    snapshot.getString("bookDate"),
                                    snapshot.getDouble("Total"));
                            bookDetailsArrayList.add(model);
                        }
                        bAdapter.notifyDataSetChanged();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookDetails.this, "Oops... Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}