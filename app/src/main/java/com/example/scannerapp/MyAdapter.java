package com.example.scannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ItemModal> itemModalArrayList;

    public MyAdapter(Context context, ArrayList<ItemModal> itemModalArrayList) {
        this.context = context;
        this.itemModalArrayList = itemModalArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_show_db,parent,false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        ItemModal item = itemModalArrayList.get(position);

        holder.itemName.setText(item.itemName);
        holder.itemPrice.setText(item.itemPrice);
        holder.itemCode.setText(item.itemCode);

    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemPrice, itemCode;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tvItemName);
            itemPrice = itemView.findViewById(R.id.tvItemPrice);
            itemCode = itemView.findViewById(R.id.tvItemCode);
        }
    }
}
