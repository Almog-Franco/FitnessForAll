package com.example.final_project;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Workout {
    private long timeStamp;
    private String type;
    private DatabaseReference databaseReference;

    public Workout(String type,long timeStamp) {
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public Workout() {
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getType() {
        return type;
    }

    public void addWorkout(String email){
        databaseReference = FirebaseDatabase.getInstance().getReference("tracking").child(email.replace(".",",")).child(UUID.randomUUID().toString());
        databaseReference.setValue(this);
    }
}
