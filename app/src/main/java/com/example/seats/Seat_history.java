package com.example.seats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Seat_history extends RecyclerView.Adapter<AvailableProductViewHolder>{
    private List<Seat> seats;
    private Seat_history.OnItemClickListener itemClickListener;


    public Seat_history(List<Seat> seats) {
        this.seats = seats;
        //this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Seat trip);
    }

    @NonNull
    @Override
    public AvailableProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);

        return new AvailableProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableProductViewHolder holder, int position) {
        Seat seat = seats.get(position);
        holder.from.setText(seat.getFrom());
        holder.to.setText(seat.getTo());
        holder.startTime.setText(seat.getStart_time());
        holder.endTime.setText(seat.getEnd_time());
        holder.total.setText("Total:"+String.valueOf(seat.getTotal()));
        holder.count.setText("Count:"+String.valueOf(seat.getTotal()));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(seat);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return seats.size();
    }
}

class AvailableProductViewHolder extends RecyclerView.ViewHolder {
    public TextView from,to,startTime,endTime,total,count;


    public AvailableProductViewHolder(@NonNull View itemView) {
        super(itemView);
        from = itemView.findViewById(R.id.from);
        to = itemView.findViewById(R.id.to);
        startTime = itemView.findViewById(R.id.startTime);
        endTime = itemView.findViewById(R.id.endTime);
        total = itemView.findViewById(R.id.total);
        count=itemView.findViewById(R.id.Count);

        // Initialize other views here
    }
}
