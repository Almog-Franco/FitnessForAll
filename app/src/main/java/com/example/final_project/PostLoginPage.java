package com.example.final_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostLoginPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostLoginPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseReference;
    Trainee trainee;

    public PostLoginPage() {
        // Required empty public constructor
    }

    public boolean canDoChallenge(int currXp, int xpToNextLevel){
        if((xpToNextLevel - currXp) <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostLoginPage.
     */
    // TODO: Rename and change types and number of parameters
    public static PostLoginPage newInstance(String param1, String param2) {
        PostLoginPage fragment = new PostLoginPage();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_login_page, container, false);
        TextView textView = view.findViewById(R.id.hello_account);
        TextView currLevelText = view.findViewById(R.id.curr_level);
        TextView currExpText = view.findViewById(R.id.curr_xp);
        TextView xpTpNextLevelText = view.findViewById(R.id.xp_left_to_next_level);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(getArguments().getString("email").replace(".",",")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainee trainee = snapshot.getValue(Trainee.class);
                textView.setText("Hello " + trainee.getFirstName() + "!");
                currLevelText.setText("Current level: " + trainee.getLevel());
                currExpText.setText("experience gained this far: " + trainee.getExperience());
                boolean permission = canDoChallenge(trainee.getExperience(),trainee.getLevel() * 50);

                if (permission){
                    xpTpNextLevelText.setText("You can do a challenge and level up!");
                }
                else{
                    xpTpNextLevelText.setText("You need extra training in order to face the challenge.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //textView.setText("Hello " + getArguments().getString("firstName") + "!");
        //currLevelText.setText("Current level: " + getArguments().getInt("level"));
        //currExpText.setText("experience gained this far: " + getArguments().getInt("xp"));
        /*boolean permission = canDoChallenge(getArguments().getInt("xp"),getArguments().getInt("level") * 50);

        if (permission){
            xpTpNextLevelText.setText("You can do a challenge and level up!");
        }
        else{
            xpTpNextLevelText.setText("You need extra training in order to face the challenge.");
        }*/

        Button homeWorkoutButton = view.findViewById(R.id.home_workout);
        Button gymWorkoutButton = view.findViewById(R.id.gym_workout);
        Button challengeButton = view.findViewById(R.id.challenge_button);
        Button reButton = view.findViewById(R.id.return_to_home_page);
        Button futureWorkout = view.findViewById(R.id.future_workout);
        Button userDashboard = view.findViewById(R.id.user_dashboard);

        futureWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_postLoginPage_to_futureWorkout);
            }
        });

        gymWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(xpTpNextLevelText.getText().toString() == "You can do a challenge and level up!"){
                    Toast.makeText(getContext(),"You are ready to complete a challenge! you need to complete a challenge to unlock more workouts.",Toast.LENGTH_LONG).show();
                }else{
                Bundle bundle = new Bundle();
                bundle.putString("email", getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_postLoginPage_to_findGymNearMe,bundle);}

            }
        });

        homeWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(xpTpNextLevelText.getText().toString() == "You can do a challenge and level up!"){

                    Toast.makeText(getContext(),"You are ready to complete a challenge! you need to complete a challenge to unlock more workouts.",Toast.LENGTH_LONG).show();

                }else {
                Bundle bundle = new Bundle();
                String level = currLevelText.getText().toString().replace("Current level: ","");
                bundle.putString("level",level);
                bundle.putString("email", getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_postLoginPage_to_homeWorkoutGear,bundle);}
            }
        });

        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(getArguments().getString("email").replace(".",",")).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Trainee trainee = snapshot.getValue(Trainee.class);
                        boolean permission = canDoChallenge(trainee.getExperience(), trainee.getLevel() * 50);

                        if (permission){
                            Bundle bundle = new Bundle();
                            String level = currLevelText.getText().toString().replace("Current level: ","");
                            bundle.putString("level",level);
                            bundle.putString("email", getArguments().getString("email"));
                            Navigation.findNavController(view).navigate(R.id.action_postLoginPage_to_challengeFragment,bundle);

                        }
                        else{
                            Toast.makeText(getContext(),"You need extra training in order to face the challenge.",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_postLoginPage_to_loginPage);
            }
        });

        userDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_postLoginPage_to_userDashboard,bundle);
            }
        });



        return view;
    }
}