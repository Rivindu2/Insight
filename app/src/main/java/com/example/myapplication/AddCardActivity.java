package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class AddCardActivity extends AppCompatActivity {
    private TextInputEditText input1 , input2 , input3 , input4;//Chamath
    private Button SBtn , VBtn;//Chamath
    private FirebaseFirestore db;
    private String uInput1, uInput2, uInput3, uInput4, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        input1=findViewById(R.id.textInputEditText);
        input2=findViewById(R.id.textInputEditText2);
        input3=findViewById(R.id.textInputEditText3);
        input4=findViewById(R.id.textInputEditText4);
        SBtn=findViewById(R.id.btn_card_save);
        VBtn=findViewById(R.id.btn_view);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            SBtn.setText("Update");
            uInput1 = bundle.getString("uInput1");
            uId = bundle.getString("uId");
            uInput2 = bundle.getString("uInput2");
            uInput3 = bundle.getString("uInput3");
            uInput4 = bundle.getString("uInput4");
            input1.setText(uInput1);
            input2.setText(uInput2);
            input3.setText(uInput3);
            input4.setText(uInput4);


        }else{
            SBtn.setText("Save");
        }

        VBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCardActivity.this , ViewCardActivity.class));
            }
        });

        SBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Input1 = input1.getText().toString();
                String Input2 = input2.getText().toString();
                String Input3 = input3.getText().toString();
                String Input4 = input4.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , Input1 , Input2 , Input3 , Input4);

                }else{
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id , Input1 , Input2 , Input3 , Input4);
                }


            }
        });


    }

    private void updateToFireStore(String id, String Input1, String Input2, String Input3, String Input4){
        db.collection("Card_Details").document(id).update("Input1" , Input1 , "Input2" , Input2 , "Input3" , Input3 , "Input4" , Input4)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddCardActivity.this, "Data Updated!!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddCardActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String Input1, String Input2, String Input3, String Input4) {
        if (!Input1.isEmpty() && !Input2.isEmpty() && !Input3.isEmpty() && !Input4.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("Input1" , Input1);
            map.put("Input2" , Input2);
            map.put("Input3" , Input3);
            map.put("Input4" , Input4);

            db.collection("Card_Details").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddCardActivity.this, "Data Saved!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddCardActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();

    }

}