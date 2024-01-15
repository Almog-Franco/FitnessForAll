package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void regFunc(View view, Trainee trainee){

        String email = trainee.getEmail();
        String password = trainee.getPassword();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeDB(trainee);
                            Toast.makeText(view.getContext(),"Registration completed successfully",Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginPage);
                        } else {
                            Toast.makeText(view.getContext(),"Registration failed, please try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    public void loginFunc(View view,String email,String password){


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(),"Login completed successfully",Toast.LENGTH_LONG).show();
                            readDB(email,view);

                        } else {
                            Toast.makeText(view.getContext(),"Login failed, please try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void readDB(String email,View view){
        // Read from the database
        myRef = FirebaseDatabase.getInstance().getReference("users").child(email.replace(".",","));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Trainee value = dataSnapshot.getValue(Trainee.class);
                Bundle bundle = new Bundle();
                bundle.putString("firstName", value.getFirstName());
                bundle.putString("lastName", value.getLastName());
                bundle.putInt("age",value.getAge());
                bundle.putString("email", value.getEmail());
                bundle.putInt("level", value.getLevel());
                bundle.putInt("xp", value.getExperience());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    public void writeDB(Trainee trainee){
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child(trainee.getEmail().replace(".",","));

        myRef.setValue(trainee);

    }


}