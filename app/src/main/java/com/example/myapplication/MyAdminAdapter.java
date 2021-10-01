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

public class MyAdminAdapter extends RecyclerView.Adapter<MyAdminAdapter.MyViewHolder> {

    private ViewMovieActivity activity;
    private List<AdminModel> mlist;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();


    public MyAdminAdapter(ViewMovieActivity activity,List<AdminModel> mlist){
        this.activity=activity;
        this.mlist=mlist;
    }
    public void updatMovie(int position){//to add adapter data back to fields
        AdminModel item=mlist.get(position);
        Bundle bundle=new Bundle();
        bundle.putString("aId",item.getId());
        bundle.putString("aName", item.getName());
        bundle.putString("aCat", item.getCat());
        bundle.putString("aDuration", item.getDuration());
        Intent intent=new Intent(activity, AddmovieActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    public void deleteMovie(int position){//to delete movie
        AdminModel item=mlist.get(position);
        db.collection("MovieDocuments").document(item.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    notifyRemoved(position);
                    Toast.makeText(activity, "Movie Deleted!!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        View v= LayoutInflater.from(activity).inflate(R.layout.amcarditem,parent,false);//display to card
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mlist.get(position).getName());
        holder.cat.setText(mlist.get(position).getCat());//getfrom model
        holder.duration.setText(mlist.get(position).getDuration());



    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,cat,duration;

        public MyViewHolder(@NonNull View itemView) {//to display to card
            super(itemView);

            name=itemView.findViewById(R.id.moviename_text);
            cat=itemView.findViewById(R.id.cat_text);
            duration=itemView.findViewById(R.id.duration_text);


        }
    }



}
