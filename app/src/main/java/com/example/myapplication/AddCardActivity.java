package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Color;
import com.google.type.Date;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AddCardActivity extends AppCompatActivity {
    private TextInputEditText input1 , input2 , input3 , input4;//Chamath
    private Button SBtn , VBtn , CBtn;//Chamath
    private FirebaseFirestore db;
    private String uInput1, uInput2, uInput3, uInput4, uId;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "AddCardActivity";
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

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
        CBtn=findViewById(R.id.btn_continue);
        mDisplayDate=(ImageView) findViewById(R.id.ic_calender);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddCardActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                input2.setText(date);
            }
        };

        fAuth = FirebaseAuth.getInstance();
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

        CBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCardActivity.this , CheckingPageActivity.class));
            }
        });

        SBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Input1 = input1.getText().toString();
                String Input2 = input2.getText().toString();
                String Input3 = input3.getText().toString();
                String Input4 = input4.getText().toString();



                if (TextUtils.isEmpty(Input1)){
                    input1.setError("Card NO is required. ");
                    return;
                }

                if (TextUtils.isEmpty(Input3)){
                    input3.setError("CVV is must");
                    return;
                }

                if (Input1.length() < 16) {
                    input1.setError("Enter Card No Correctly!!");
                    return;
                }

                if (Input3.length() > 3) {
                    input3.setError("Enter Only 3 digits!!");
                    return;
                }

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , Input1 , Input2 , Input3 , Input4);

                    input1.getText().clear();
                    input2.getText().clear();
                    input3.getText().clear();
                    input4.getText().clear();


                }else{
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id , Input1 , Input2 , Input3 , Input4);

                    input1.getText().clear();
                    input2.getText().clear();
                    input3.getText().clear();
                    input4.getText().clear();

                }

            }
        });
    }

    private void updateToFireStore(String id, String Input1, String Input2, String Input3, String Input4){
        userId = fAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("CardDetails").document(id).update("Input1" , Input1 , "Input2" , Input2 , "Input3" , Input3 , "Input4" , Input4)
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

            userId = fAuth.getCurrentUser().getUid();

            db.collection("users").document(userId).collection("CardDetails").document(id).set(map)
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



