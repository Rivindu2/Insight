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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class feedAdapter  extends RecyclerView.Adapter<feedAdapter.MyViewHolder> {
    private showfeedActivity activity;
    private List<feedModel> mlist;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public feedAdapter (showfeedActivity activity,List<feedModel> mlist){
        this.activity=activity;
        this.mlist=mlist;
    }

    public void updateFeed(int position){
        feedModel item =mlist.get(position);
        Bundle bundle=new Bundle();
        bundle.putString("uID",item.getId());
        bundle.putString("uName",item.getName());
        bundle.putString("uFeedback",item.getFeedback());
        Intent intent = new Intent(activity,feedbackActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    public void deleteData(int position){
        feedModel item = mlist.get(position);
        db.collection("Feedback_details").document(item.getId()).delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        notifyRemoved(position);
                        Toast.makeText(activity, "Feedback Deleted!", Toast.LENGTH_SHORT).show();
                        
                    }else {
                        Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    private void notifyRemoved(int position){
        mlist.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.feed,parent,false);
                return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Name.setText(mlist.get(position).getName());
        holder.Feedback.setText(mlist.get(position).getFeedback());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Feedback;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.name_text);
            Feedback=itemView.findViewById(R.id.feedback_text);
        }
    }
}
