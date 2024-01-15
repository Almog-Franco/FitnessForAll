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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartHomeWorkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartHomeWorkout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DrillAdapter drillAdapter;
    ArrayList<Drill> list;

    public StartHomeWorkout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartHomeWorkout.
     */
    // TODO: Rename and change types and number of parameters
    public static StartHomeWorkout newInstance(String param1, String param2) {
        StartHomeWorkout fragment = new StartHomeWorkout();
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
      View view = inflater.inflate(R.layout.fragment_start_home_workout, container, false);
      Button homeWorkoutFinishButton = view.findViewById(R.id.home_finish_button);
      String level = getArguments().getString("level");
      String literalLevel = "";
      switch (level){
          case "1": literalLevel = "level1";
                  break;
          case "2": literalLevel = "level2";
              break;
          case "3": literalLevel = "level3";
              break;
          case "4": literalLevel = "level4";
              break;
          case "5": literalLevel = "level5";
              break;
      }

      recyclerView = view.findViewById(R.id.drills_rec_view);
      databaseReference = FirebaseDatabase.getInstance().getReference("workouts").child(literalLevel);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      list = new ArrayList<>();
      ArrayList<String> additionalDrills = getArguments().getStringArrayList("gear");

      drillAdapter = new DrillAdapter(getContext(),list);
      recyclerView.setAdapter(drillAdapter);
        if(additionalDrills.size() > 3){
            for(int i = 0;i<3;i++){
                int size = additionalDrills.size();
                int randomIndex = (int)(Math.random() * size);
                String currItem = additionalDrills.get(randomIndex);
                switch (currItem){
                    case "towel":{
                        int randomTowelIndex = (int)Math.random() * MyData.towel.length;
                        Drill drill = new Drill(MyData.towel[randomTowelIndex],MyData.towelVideos[randomTowelIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "book":{
                        int randomBookIndex = (int)Math.random() * MyData.book.length;
                        Drill drill = new Drill(MyData.book[randomBookIndex],MyData.bookVideos[randomBookIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "chair":{
                        int randomChairIndex = (int)Math.random() * MyData.chair.length;
                        Drill drill = new Drill(MyData.chair[randomChairIndex],MyData.chairVideos[randomChairIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "plastic plate":{
                        int randomPlasticPlateIndex = (int)Math.random() * MyData.plasticPlate.length;
                        Drill drill = new Drill(MyData.plasticPlate[randomPlasticPlateIndex],MyData.plasticPlateVideos[randomPlasticPlateIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "broomstick":{
                        int randomBroomStickIndex = (int)Math.random() * MyData.broomstick.length;
                        Drill drill = new Drill(MyData.broomstick[randomBroomStickIndex],MyData.broomstickVideos[randomBroomStickIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "canned food":{
                        int randomCannedFoodIndex = (int)Math.random() * MyData.cannedFood.length;
                        Drill drill = new Drill(MyData.cannedFood[randomCannedFoodIndex],MyData.cannedFoodVideos[randomCannedFoodIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "Water bottles":{
                        int randomWaterBottlesIndex = (int)Math.random() * MyData.waterBottle.length;
                        Drill drill = new Drill(MyData.waterBottle[randomWaterBottlesIndex],MyData.waterBottleVideos[randomWaterBottlesIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                }
                additionalDrills.remove(randomIndex);
            }
        }
        else if(additionalDrills.size()<=3 && additionalDrills.size() > 0){

            for(int i = 0; i < additionalDrills.size();i++){
                String currItem = additionalDrills.get(i);
                switch (currItem){
                    case "towel":{
                        int randomTowelIndex = (int)Math.random() * MyData.towel.length;
                        Drill drill = new Drill(MyData.towel[randomTowelIndex],MyData.towelVideos[randomTowelIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "book":{
                        int randomBookIndex = (int)Math.random() * MyData.book.length;
                        Drill drill = new Drill(MyData.book[randomBookIndex],MyData.bookVideos[randomBookIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "chair":{
                        int randomChairIndex = (int)Math.random() * MyData.chair.length;
                        Drill drill = new Drill(MyData.chair[randomChairIndex],MyData.chairVideos[randomChairIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "plastic plate":{
                        int randomPlasticPlateIndex = (int)Math.random() * MyData.plasticPlate.length;
                        Drill drill = new Drill(MyData.plasticPlate[randomPlasticPlateIndex],MyData.plasticPlateVideos[randomPlasticPlateIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "broomstick":{
                        int randomBroomStickIndex = (int)Math.random() * MyData.broomstick.length;
                        Drill drill = new Drill(MyData.broomstick[randomBroomStickIndex],MyData.broomstickVideos[randomBroomStickIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "canned food":{
                        int randomCannedFoodIndex = (int)Math.random() * MyData.cannedFood.length;
                        Drill drill = new Drill(MyData.cannedFood[randomCannedFoodIndex],MyData.cannedFoodVideos[randomCannedFoodIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                    case "Water bottles":{
                        int randomWaterBottlesIndex = (int)Math.random() * MyData.waterBottle.length;
                        Drill drill = new Drill(MyData.waterBottle[randomWaterBottlesIndex],MyData.waterBottleVideos[randomWaterBottlesIndex],"15","3" );
                        list.add(drill);
                        break;
                    }
                }
            }

        }

      databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                  Drill drill = dataSnapshot.getValue(Drill.class);
                  list.add(drill);

              }

              drillAdapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

        homeWorkoutFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workout workout = new Workout("Home workout",System.currentTimeMillis());
                workout.addWorkout(getArguments().getString("email"));
                Bundle bundle = new Bundle();
                bundle.putString("email", getArguments().getString("email"));
                Trainee trainee = new Trainee();
                trainee.addXp(getArguments().getString("email"),5 * list.size());
                Navigation.findNavController(view).navigate(R.id.action_startHomeWorkout_to_postLoginPage,bundle);
            }
        });


      return view;
    }
}