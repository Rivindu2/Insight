package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bookDetailsAdapter extends RecyclerView.Adapter<bookDetailsAdapter.MyViewHolder>{

    Context context;
    ArrayList<BookDetailsModel> BookDetailsModelArrayList;


    public bookDetailsAdapter(Context context, ArrayList<BookDetailsModel>BookDetailsModelArrayList){
        this.context=context;
        this.BookDetailsModelArrayList=BookDetailsModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.booking, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BookDetailsModel model=BookDetailsModelArrayList.get(position);
        holder.movieName.setText(model.movieName);
        holder.id.setText(model.id);
        holder.date.setText(model.bookDate);
        holder.noOfSeats.setText(model.No_Of_Seats);
        holder.Total.setText(model.Total);
    }

    @Override
    public int getItemCount() {
        return BookDetailsModelArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id, movieName, date,noOfSeats, Total;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.tId);
            movieName=itemView.findViewById(R.id.mName);
            date=itemView.findViewById(R.id.bDate);
            noOfSeats=itemView.findViewById(R.id.noSeats);
            Total=itemView.findViewById(R.id.Total);

        }
    }


}



