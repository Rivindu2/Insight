package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Cusbooking extends AppCompatActivity {
    private TextInputEditText Mid , Mname , Noofseats , Showdate;//Chamath
    private TextView Tprice;
    private Button EBtn;//Chamath
    private FirebaseFirestore db;
    private String uInput1, uInput2, uInput3, uInput4, uId;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusbooking);

        Mid=findViewById(R.id.textView20);
        Mname=findViewById(R.id.textView21);
        Noofseats=findViewById(R.id.textView22);
        Showdate=findViewById(R.id.textView23);
        Tprice=findViewById(R.id.textView24);
        EBtn=findViewById(R.id.btn10);


        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        EBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float addition=Integer.parseInt(Noofseats.getText().toString());
                float tprice = addition * 500;
                String mid = Mid.getText().toString();
                String mname = Mname.getText().toString();
                String noofseats = Noofseats.getText().toString();
                String showdate = Showdate.getText().toString();


                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    /*updateToFireStore(id , mid , mname , noofseats , showdate , tprice);*/

                }else{
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id , mid , mname , noofseats , showdate , tprice);
                }

            }
        });

    }

    private void saveToFireStore(String id, String mid, String mname, String noofseats, String showdate, float tprice) {
        if (!mid.isEmpty() && !mname.isEmpty() && !noofseats.isEmpty() && !showdate.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("mid" , mid);
            map.put("mname" , mname);
            map.put("noofseats" , noofseats);
            map.put("showdate" , showdate);
            map.put("tprice" , tprice);

            userId = fAuth.getCurrentUser().getUid();

            db.collection("users").document(userId).collection("BookingInfo").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Cusbooking.this, "Data Saved!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cusbooking.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}