package com.example.final_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    WorkoutAdapter workoutAdapter;
    ArrayList<Workout> list;

    public UserDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDashboard newInstance(String param1, String param2) {
        UserDashboard fragment = new UserDashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);
        EditText lastWeight = view.findViewById(R.id.lastWeekWeight);
        EditText currentWeight = view.findViewById(R.id.current_weight);
        Button submit = view.findViewById(R.id.dashboard_submit);
        Button back = view.findViewById(R.id.dashboard_back_button);
        recyclerView = view.findViewById(R.id.past_week_workouts);
        databaseReference = FirebaseDatabase.getInstance().getReference("tracking").child(getArguments().getString("email").replace(".",","));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        workoutAdapter = new WorkoutAdapter(getContext(),list);
        recyclerView.setAdapter(workoutAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Workout workout = dataSnapshot.getValue(Workout.class);
                    if(System.currentTimeMillis() - 604800000 <= workout.getTimeStamp()){
                        list.add(workout);
                    }
                    else{
                        continue;
                    }

                }
                workoutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastWeek = lastWeight.getText().toString();
                String currWeek = currentWeight.getText().toString();
                try {
                    int lastWeekEight = Integer.parseInt(lastWeek);
                    int currentWeek = Integer.parseInt(currWeek);
                    if(lastWeekEight > currentWeek){
                        Toast.makeText(getContext(),"Well done! nice weight loss!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(),"Some more workout is needed :)",Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception e){
                    Toast.makeText(getContext(),"The weight entered is not a number",Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email",getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_userDashboard_to_postLoginPage,bundle);
            }
        });




        return view;
    }
}