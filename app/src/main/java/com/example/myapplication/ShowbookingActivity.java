package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShowbookingActivity extends AppCompatActivity {

//    private RecyclerView recyclerView;
//    private FirebaseFirestore db;
//    private MyBookingAdapter adapter;
//    private List<BookDetailsModel> list;
//    String userId;
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_showbooking);
//
//        recyclerView = findViewById(R.id.recyclerView10);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        fAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//        list = new ArrayList<BookDetailsModel>();
//        adapter = new MyBookingAdapter(this , list);
//        recyclerView.setAdapter(adapter);
//
//        ItemTouchHelper touchHelper = new ItemTouchHelper(new BookTouchHelper(adapter));
//        touchHelper.attachToRecyclerView(recyclerView);
//
//        showData();
//
//
//    }
//
//    public void showData(){
//        userId = fAuth.getCurrentUser().getUid();
//
//
//        db.collection("users").document(userId).collection("BookingInfo").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        list.clear();
//                        for (DocumentSnapshot snapshot : task.getResult()){
//
//                            BookDetailsModel model = new BookDetailsModel(snapshot.getString("id") , snapshot.getString("mid") , snapshot.getString("mname") , snapshot.getString("noofseats") , snapshot.getString("showdate") , snapshot.getString("tprice"));
//                            list.add(model);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ShowbookingActivity.this, "Oops.. Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });


//    }


}