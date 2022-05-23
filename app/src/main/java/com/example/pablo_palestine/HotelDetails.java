package com.example.pablo_palestine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pablo_palestine.databinding.ActivityHotelDetailsBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.hotel_room.RoomsActivity;
import com.example.pablo_palestine.hotels.Hotels;
import com.example.pablo_palestine.navbar2.HotelsFragment;

public class HotelDetails extends AppCompatActivity {

    String name, locations, rate, details1, img;
    Hotels hotels;
    ActivityHotelDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//----------------------------reserve-------------------------------------------------
        name = binding.hotelName.getText().toString();
        locations = binding.locationPin.getText().toString();
        rate = binding.rate.getText().toString();
        details1 = binding.details.getText().toString();

        Intent intent = getIntent();
        hotels = intent.getParcelableExtra(HotelsFragment.Item_KEY);

        name = hotels.getName();
        locations = hotels.getLocation();
        rate = String.valueOf(hotels.getRate());
        details1 = hotels.getDetails();
        img = hotels.getImage();

        binding.hotelName.setText(name);
        binding.locationPin.setText(locations);
        binding.rate.setText(rate);
        binding.details.setText(details1);
        Glide.with(this).load(img).into(binding.imageView);

//--------------------------------------------------------------------------------
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getBaseContext(), RoomsActivity.class);
                intent1.putExtra("room", hotels.getKey());
                startActivity(intent1);
            }
        });

    }


}