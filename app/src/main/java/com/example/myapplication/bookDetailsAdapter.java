package com.example.myapplication;

import android.content.Context;
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

import java.util.ArrayList;

public class bookDetailsAdapter extends RecyclerView.Adapter<bookDetailsAdapter.MyViewHolder>{

    Context context;
    ArrayList<BookDetailsModel> BookDetailsModelArrayList;
    private FirebaseAuth fAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String userId;
    BookDetails bk;
    public bookDetailsAdapter(BookDetails bk, ArrayList<BookDetailsModel>BookDetailsModelArrayList){
        this.bk=bk;
        this.BookDetailsModelArrayList=BookDetailsModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(bk).inflate(R.layout.booking, parent, false);
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

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent o=new Intent(v.getContext(), RetrievedetailsActivity.class);

                    o.putExtra("id", BookDetailsModelArrayList.get(getAdapterPosition()).getId());
                    o.putExtra("movieName", BookDetailsModelArrayList.get(getAdapterPosition()).getMovieName());
                    o.putExtra("No_Of_Seats", BookDetailsModelArrayList.get(getAdapterPosition()).getBookDate());
                    o.putExtra("bookDate", BookDetailsModelArrayList.get(getAdapterPosition()).getNoofseats());
                    o.putExtra("Total", BookDetailsModelArrayList.get(getAdapterPosition()).getTotal());

                    v.getContext().startActivity(o);
                }
            });



        }
    }

    public void updateData(int position){

        BookDetailsModel item=BookDetailsModelArrayList.get(position);
        Bundle bundle=new Bundle();
        bundle.putString("id", item.getId());
        bundle.putString("mTitle", item.getMovieName());
        bundle.putString("eDate", item.getBookDate());
        bundle.putString("seatsNum", item.getNoofseats());

        Intent i=new Intent(bk, updateBooking.class);
        i.putExtras(bundle);
        bk.startActivity(i);

    }

    public void deleteData(int position){
        userId=fAuth.getCurrentUser().getUid();

        BookDetailsModel item=BookDetailsModelArrayList.get(position);
        db.collection("users").document(userId).collection("MovieBookings").document(item.getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    notifyRemoved(position);
                    Toast.makeText(bk,"The ticket has been cancelled...",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(bk,"Error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void notifyRemoved(int position){
        BookDetailsModelArrayList.remove(position);
        notifyItemRemoved(position);
        bk.showData();
    }

}



