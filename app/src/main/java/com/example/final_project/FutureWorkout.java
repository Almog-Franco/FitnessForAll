package com.example.final_project;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.final_project.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FutureWorkout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FutureWorkout extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivityMainBinding binding;
    TextView selectedTime;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker picker;

    public FutureWorkout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FutureWorkout.
     */
    // TODO: Rename and change types and number of parameters
    public static FutureWorkout newInstance(String param1, String param2) {
        FutureWorkout fragment = new FutureWorkout();
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
      View view = inflater.inflate(R.layout.fragment_future_workout, container, false);
      Button setAlarm = view.findViewById(R.id.setAlarmBtn);
      Button cancelAlarm = view.findViewById(R.id.cancelAlarmBtn);
      createNotificationChannel();
      picker = view.findViewById(R.id.timePicker);




      setAlarm.setOnClickListener(new View.OnClickListener() {
          @RequiresApi(api = Build.VERSION_CODES.M)
          @Override
          public void onClick(View v) {
              setAlarm();

          }
      });

      cancelAlarm.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              cancelAlarm();

          }
      });



      return view;
    }

    private void cancelAlarm() {
        Intent intent = new Intent(getContext(),AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,PendingIntent.FLAG_IMMUTABLE);

        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(getContext(),"Alarm Canceled",Toast.LENGTH_LONG).show();

    }


    private void setAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
        calendar.set(Calendar.MINUTE,picker.getMinute());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(),AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        Toast.makeText(getContext(),"Reminder set!",Toast.LENGTH_LONG).show();
    }
    }


    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "FitnessForAllChannel";
            String description = "Channel for Alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("FitnessForAll",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}