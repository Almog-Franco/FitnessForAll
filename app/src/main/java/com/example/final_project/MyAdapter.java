package com.example.final_project;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> itemArrayList;
    private SparseBooleanArray checkedState = new SparseBooleanArray(); //array of checked items

    public MyAdapter(Context context, ArrayList<Item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Item item = itemArrayList.get(position);
        holder.description.setText(item.description);
        holder.itemImage.setImageResource(item.image);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setCheckedState(holder.getAdapterPosition(),isChecked);


            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView description;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            description = itemView.findViewById(R.id.item_text);
            checkBox = itemView.findViewById(R.id.isChecked);
        }
    }

    public void setCheckedState(int position,boolean checked){
        this.checkedState.append(position, checked);
    }

    public boolean isItemChecked(int position){
        return this.checkedState.get(position);
    }

    public ArrayList<Item> getListItems() {
        return this.itemArrayList;
    }

    public Item getItem(int position) {
        return itemArrayList.get(position);
    }


}
