package com.example.pablo_palestine.navbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.FragmentChurchesBinding;
import com.example.pablo_palestine.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private static final int REQUEST_CODE = 1234;
    FragmentMapsBinding binding;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public void MainBranchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        binding.microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakButtonClicked(view);
            }
        });
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = binding.placesSearch.getText().toString();
                if (location == null) {
                    Toast.makeText(getActivity(), "null location", Toast.LENGTH_SHORT).show();
                }else {
                    Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address>addressList=geocoder.getFromLocationName(location,1);
                        if (addressList.size()>0){
                            LatLng latLng=new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Gaza City"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20.0f));
                            Toast.makeText(getActivity(), ""+latLng.latitude+"  /  "+latLng.longitude, Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // gaza   31.501509638780096            34.467377588152885
        // church  31.503768518902422           34.46227602660656
        LatLng sydney = new LatLng(31.504656676636078, 34.464361108839505);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Al omari mosque"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Gaza City"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
                Log.e("map", latLng.latitude + "  /  " + latLng.longitude);
                Toast.makeText(getActivity(), "" + latLng.latitude + "  /  " + latLng.longitude, Toast.LENGTH_SHORT).show();

            }
        });

//microphone
        // Disable button if no recognition service is present
        PackageManager pm = getActivity().getPackageManager();
        List <ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            binding.microphone.setEnabled(true);
            binding.placesSearch.setText("Recognizer not present");
        }
        binding.placesSearch.addTextChangedListener(new TextWatcher()
        {@Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            // TODO Auto-generated method stub
        }@Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // TODO Auto-generated method stub
        }@Override
        public void afterTextChanged(Editable s)
        {
            // TODO Auto-generated method stub
            binding.microphone.setEnabled(true);
        }
        });
    }
    /**
     * Handle the action of the button being clicked
     */

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList< String > matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty())
            {
                String Query = matches.get(0);
                binding.placesSearch.setText(Query);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }


}