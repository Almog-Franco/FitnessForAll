package com.example.final_project;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrillAdapter extends RecyclerView.Adapter<DrillAdapter.MyViewHolder> {

    Context context;
    ArrayList<Drill> list;
    String link;
    public DrillAdapter(Context context, ArrayList<Drill> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.drill,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Drill drill = list.get(position);
        holder.drillName.setText(drill.getName());
        holder.reps.setText(drill.getReps());
        holder.sets.setText(drill.getSets());
        holder.demo.setText("Click here for a demonstration " + drill.getLink());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView drillName, reps, sets,demo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            drillName = itemView.findViewById(R.id.drill_name);
            demo = itemView.findViewById(R.id.link);
            reps = itemView.findViewById(R.id.reps_amount);
            sets = itemView.findViewById(R.id.sets_amount);
        }
    }


}
