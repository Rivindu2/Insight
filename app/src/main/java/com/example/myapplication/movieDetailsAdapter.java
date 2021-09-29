package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class movieDetailsAdapter extends RecyclerView.Adapter<movieDetailsAdapter.MyViewHolder>{

    Context context;
    ArrayList<movieDetailsModel> movieDetailsModelArrayList;
    HomeActivity hm;
    Button button;
    List<String> data;
    Bundle args = new Bundle();
    movieDetailsModel movieDetails;
    public movieDetailsAdapter(Context context, ArrayList<movieDetailsModel> movieDetailsModelArrayList) {
        this.context = context;
        this.movieDetailsModelArrayList = movieDetailsModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.movies, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        movieDetailsModel model=movieDetailsModelArrayList.get(position);
        holder.name.setText(model.name);
        holder.cat.setText(model.cat);
        holder.duration.setText(model.duration);

    }

    @Override
    public int getItemCount() {
        return movieDetailsModelArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, cat, duration ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.mName);
            cat=itemView.findViewById(R.id.category);
            duration=itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent i=new Intent(v.getContext(), BookNow.class);

                    i.putExtra("name", movieDetailsModelArrayList.get(getAdapterPosition()).getName());
                    i.putExtra("cat", movieDetailsModelArrayList.get(getAdapterPosition()).getCat());
                    i.putExtra("duration", movieDetailsModelArrayList.get(getAdapterPosition()).getDuration());


                    v.getContext().startActivity(i);
                }
            });
        }


    }

}
