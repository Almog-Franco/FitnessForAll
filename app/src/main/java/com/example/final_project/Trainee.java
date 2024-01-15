package com.example.final_project;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Trainee {
    private String firstName;
    private String lastName;
    private int age;
    public String id;
    private String email;
    private String password;
    private int level;
    private int experience;
    private DatabaseReference databaseReference;

    public Trainee(String firstName, String lastName, int age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.level = 1;
        this.experience = 0;
        this.id = UUID.randomUUID().toString();
    }

    public Trainee(){

    }

    public int getExperience() {
        return experience;
    }

    public String getId() {
        return id;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addXp(String email, int amount){
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

       databaseReference.child(email.replace('.',',')).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Trainee trainee = snapshot.getValue(Trainee.class);
               databaseReference.child(email.replace('.',',')).child("experience").setValue(trainee.getExperience() + amount);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });




    }

    public void addLevel(String email){
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.child(email.replace('.',',')).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainee trainee = snapshot.getValue(Trainee.class);
                databaseReference.child(email.replace('.',',')).child("level").setValue(trainee.getLevel() + 1);
                databaseReference.child(email.replace('.',',')).child("experience").setValue(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
