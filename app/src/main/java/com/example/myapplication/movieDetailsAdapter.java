package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class movieDetailsAdapter extends RecyclerView.Adapter<movieDetailsAdapter.MyViewHolder>{

    Context context;
    ArrayList<movieDetailsModel> movieDetailsModelArrayList;

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
    public void onBindViewHolder(@NonNull movieDetailsAdapter.MyViewHolder holder, int position) {

        movieDetailsModel model=movieDetailsModelArrayList.get(position);
        holder.name.setText(model.name);
        holder.cat.setText(model.cat);
        holder.duration.setText(model.duration);

    }

    @Override
    public int getItemCount() {
        return movieDetailsModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, cat, duration ,show_start_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            cat=itemView.findViewById(R.id.cat);
            duration=itemView.findViewById(R.id.duration);


        }
    }
}
