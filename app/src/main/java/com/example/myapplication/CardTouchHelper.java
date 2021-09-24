package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CardTouchHelper extends ItemTouchHelper.SimpleCallback {

    private MyCardAdapter adapter;
    public CardTouchHelper(MyCardAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT){
            adapter.updatecardData(position);
            adapter.notifyDataSetChanged();
        }else{
            adapter.deletecardData(position);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(Color.parseColor("#962A1A"))
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeLeftBackgroundColor(Color.parseColor("#006400"))
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                .create()
                .decorate();


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
