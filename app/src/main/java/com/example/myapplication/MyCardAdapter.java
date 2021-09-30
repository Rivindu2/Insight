package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.MyViewCardHolder> {
    private ViewCardActivity activity;
    private List<CardModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    public MyCardAdapter(ViewCardActivity activity , List<CardModel> mList){
        this.activity = activity;
        this.mList = mList;

    }

    public  void updatecardData(int position){
        CardModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());
        bundle.putString("uInput1" , item.getInput1());
        bundle.putString("uInput2" , item.getInput2());
        bundle.putString("uInput3" , item.getInput3());
        bundle.putString("uInput4" , item.getInput4());
        Intent intent = new Intent(activity , AddCardActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deletecardData(int position){
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        CardModel item = mList.get(position);
        db.collection("users").document(userId).collection("CardDetails").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data deleted!!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error : " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public MyViewCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.carddetails , parent , false);
        return new MyViewCardHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewCardHolder holder, int position) {
        CardModel model = mList.get(position);
        /*holder.Input1.setText(mList.get(position).getInput1());
        holder.Input2.setText(mList.get(position).getInput2());
        holder.Input3.setText(mList.get(position).getInput3());
        holder.Input4.setText(mList.get(position).getInput4());*/
        holder.Input1.setText(model.Input1);
        holder.Input2.setText(model.Input2);
        holder.Input3.setText(model.Input3);
        holder.Input4.setText(model.Input4);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewCardHolder extends RecyclerView.ViewHolder{

        TextView Input1 , Input2 , Input3 , Input4;

        public MyViewCardHolder(@NonNull View itemView) {
            super(itemView);

            Input1 = itemView.findViewById(R.id.Input1_text);
            Input2 = itemView.findViewById(R.id.Input2_text);
            Input3 = itemView.findViewById(R.id.Input3_text);
            Input4 = itemView.findViewById(R.id.Input4_text);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent i=new Intent(v.getContext(), CheckingPagenewActivity.class);

                    i.putExtra("Input1", mList.get(getAdapterPosition()).getInput1());
                    i.putExtra("Input2", mList.get(getAdapterPosition()).getInput2());
                    i.putExtra("Input3", mList.get(getAdapterPosition()).getInput3());
                    i.putExtra("Input4", mList.get(getAdapterPosition()).getInput4());


                    v.getContext().startActivity(i);
                }
            });
        }
    }

}