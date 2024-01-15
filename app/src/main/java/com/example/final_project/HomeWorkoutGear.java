package com.example.final_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeWorkoutGear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeWorkoutGear extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Item> itemArrayList;
    private RecyclerView recyclerView;

    public HomeWorkoutGear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeWorkoutGear.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeWorkoutGear newInstance(String param1, String param2) {
        HomeWorkoutGear fragment = new HomeWorkoutGear();
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
        View view = inflater.inflate(R.layout.fragment_home_workout_gear, container, false);

        Button button = view.findViewById(R.id.home_workout_next_button);
        ArrayList<String> descArray = new ArrayList<String>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < recyclerView.getAdapter().getItemCount();i++){
                    if(((MyAdapter)recyclerView.getAdapter()).isItemChecked(i)){
                            Item currItem = ((MyAdapter) recyclerView.getAdapter()).getItem(i);
                            descArray.add(currItem.getDescription());


                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("level",getArguments().getString("level"));
                bundle.putStringArrayList("gear",descArray);
                bundle.putString("email", getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_homeWorkoutGear_to_startHomeWorkout,bundle);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();
        recyclerView = view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getContext(),itemArrayList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void dataInitialize(){
        itemArrayList = new ArrayList<Item>();

        for(int i=0 ; i<MyData.descArray.length ; i++)
        {
            itemArrayList.add(new Item(
                    MyData.descArray[i],
                    MyData.imageArray[i]
            ));
        }
    }
}