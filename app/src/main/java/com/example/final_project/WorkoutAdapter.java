package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder> {

    Context context;
    ArrayList<Workout> list;

    public WorkoutAdapter(Context context, ArrayList<Workout> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_wokout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Workout workout = list.get(position);
        Timestamp timestamp = new Timestamp(workout.getTimeStamp());
        holder.timeStamp.setText(timestamp.toString());
        holder.type.setText(workout.getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timeStamp, type;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            timeStamp = itemView.findViewById(R.id.timeStamp);
            type = itemView.findViewById(R.id.workoutType);
        }
    }
}
