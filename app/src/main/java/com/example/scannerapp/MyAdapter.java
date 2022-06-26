package com.example.scannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<ItemModal> itemModalArrayList;
    List<ItemModal> fullItemList;

    public MyAdapter(Context context, ArrayList<ItemModal> itemModalArrayList) {
        this.context = context;
        this.itemModalArrayList = itemModalArrayList;
        this.fullItemList = new ArrayList<>(itemModalArrayList);
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

        holder.itemName.setText(item.name);
        holder.itemPrice.setText(item.price);
        holder.itemCode.setText(item.code);

    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }
    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ItemModal> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0){
                filteredList.addAll(filteredList);


            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ItemModal itemModal : itemModalArrayList){
                    if(itemModal.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(itemModal);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            itemModalArrayList.clear();
            itemModalArrayList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };


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
