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
//
//public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {
//    private ShowbookingActivity activity;
//    private List<BookDetailsModel> mList;
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    String userId;
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//
//
//
//
//    public MyBookingAdapter(ShowbookingActivity activity , List<BookDetailsModel> mList){
//       this.activity = activity;
//       this.mList = mList;
//   }
//
//   public void updateBookData(int position){
//       BookDetailsModel item = mList.get(position);
//       Bundle bundle = new Bundle();
//       bundle.putString("uId" , item.getId());
//       bundle.putString("uMname" , item.getMname());
//       bundle.putString("uNoofseats" , item.getNoofseats());
//       bundle.putString("uShowdate" , item.getShowdate());
//       Intent intent = new Intent(activity , Cusbooking.class);
//       intent.putExtras(bundle);
//       activity.startActivity(intent);
//   }
//
//   public void deleteBookData(int position){
//       fAuth = FirebaseAuth.getInstance();
//       userId = fAuth.getCurrentUser().getUid();
//       BookDetailsModel item = mList.get(position);
//       db.collection("users").document(userId).collection("BookingInfo").document(item.getId()).delete()
//               .addOnCompleteListener(new OnCompleteListener<Void>() {
//                   @Override
//                   public void onComplete(@NonNull Task<Void> task) {
//                       if (task.isSuccessful()){
//                           notifyRemoved(position);
//                           Toast.makeText(activity, "Data deleted!!!", Toast.LENGTH_SHORT).show();
//                       }else{
//                           Toast.makeText(activity, "Error : " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                       }
//                   }
//               });
//   }
//
//    private void notifyRemoved(int position){
//        mList.remove(position);
//        notifyItemRemoved(position);
//        activity.showData();
//    }
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(activity).inflate(R.layout.bookitem , parent , false);
//        return new MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.mid.setText(mList.get(position).getMid());
//        holder.mname.setText(mList.get(position).getMname());
//        holder.noofseats.setText(mList.get(position).getNoofseats());
//        holder.showdate.setText(mList.get(position).getShowdate());
//        holder.tprice.setText(mList.get(position).getTprice());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//
//        TextView mid , mname , noofseats , showdate , tprice;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            mid = itemView.findViewById(R.id.midview);
//            mname = itemView.findViewById(R.id.mnameview);
//            noofseats = itemView.findViewById(R.id.noofseatsview);
//            showdate = itemView.findViewById(R.id.showdateview);
//            tprice = itemView.findViewById(R.id.tpriceview);
//
//        }
//    }
//}
