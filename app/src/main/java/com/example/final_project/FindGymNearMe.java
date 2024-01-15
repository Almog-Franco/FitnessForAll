package com.example.final_project;


import static androidx.browser.customtabs.CustomTabsClient.getPackageName;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindGymNearMe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindGymNearMe extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isPermissionsGranted;
    private List<String> providers;
    MapView mapView;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng latLng;
    GoogleMap googleMap;
    Location myLocation;
    LocationManager locationManager;


    public FindGymNearMe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindGymNearMe.
     */
    // TODO: Rename and change types and number of parameters
    public static FindGymNearMe newInstance(String param1, String param2) {
        FindGymNearMe fragment = new FindGymNearMe();
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
        View view = inflater.inflate(R.layout.fragment_find_gym_near_me, container, false);
        mapView = view.findViewById(R.id.my_map);
        //checkPermissions();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        providers = locationManager.getProviders(criteria, false);
        getMyLocation();
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        Button goBackButt = view.findViewById(R.id.back_button_gyms_near_me);
        Button nextButt = view.findViewById(R.id.next_button_gyms_near_me);
        goBackButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_findGymNearMe_to_postLoginPage, bundle);
            }
        });
        nextButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", getArguments().getString("email"));
                Navigation.findNavController(view).navigate(R.id.action_findGymNearMe_to_startGymWorkout, bundle);
            }
        });


        return view;
    }

    private void getMyLocation() {
        if (providers.isEmpty() != true) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityResultLauncher<String[]> locationPermissionRequest =
                        registerForActivityResult(new ActivityResultContracts
                                        .RequestMultiplePermissions(), result -> {
                                    Boolean fineLocationGranted = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        fineLocationGranted = result.getOrDefault(
                                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                                    }
                                    Boolean coarseLocationGranted = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        coarseLocationGranted = result.getOrDefault(
                                                Manifest.permission.ACCESS_COARSE_LOCATION,false);
                                    }
                                    if (fineLocationGranted != null && fineLocationGranted) {
                                        // Precise location access granted.
                                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                        // Only approximate location access granted.
                                    } else {
                                        // No location access granted.
                                    }
                                }
                        );

                locationPermissionRequest.launch(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                });
            } else {
                for (String provider : providers) {
                    myLocation = locationManager.getLastKnownLocation(provider);
                    if (locationManager == null) {
                        continue;
                    }

                }
            }
        }
        if (myLocation == null) {
            //getLastLocation();
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        try{
        this.googleMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Your location");
        latLng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,10);
        googleMap.moveCamera(cameraUpdate);
        getNearLocations();}
        catch (Exception e){
            Toast.makeText(getContext(),"There was a location error", Toast.LENGTH_LONG).show();
        }
    }

    private void getNearLocations() {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=" + myLocation.getLatitude() + "," + myLocation.getLongitude() +
                "&radius=10000" +
                "&types=gym" +
                "&sensor=true" +
                "&key=AIzaSyD2B7SfCH9vPhQa0srJnPfwlszqR6lFx7A";

        new PlaceTask().execute(url);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException{
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer,List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            for(int i = 0;i < hashMaps.size();i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");
                LatLng latLng = new LatLng(lat,lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                googleMap.addMarker(options);
            }
        }
    }
}