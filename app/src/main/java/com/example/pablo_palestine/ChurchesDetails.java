package com.example.pablo_palestine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo_palestine.databinding.ActivityCemeteryBinding;
import com.example.pablo_palestine.databinding.ActivityConfirmPaymentBinding;
import com.example.pablo_palestine.databinding.ActivityHotelDetailsBinding;
import com.example.pablo_palestine.databinding.ActivityMainBinding;
import com.example.pablo_palestine.navbar2.ChurchesFragment;
import com.example.pablo_palestine.navbar2.MosqueFragment;
import com.example.pablo_palestine.test.BottomNavigationBarActivity;

import kr.co.prnd.readmore.ReadMoreTextView;

public class ChurchesDetails extends AppCompatActivity {
    String url = "https://images.unsplash.com/photo-1597248374161-426f0d6d2fc9?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YmVhdXRpZnVsJTIwd29tYW58ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80";
    String url1 = "https://thumbs.dreamstime.com/b/beauty-woman-face-portrait-beautiful-model-girl-perfect-fresh-clean-skin-spa-brunette-female-looking-camera-smiling-66363112.jpg";
    String url2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-mCcvKZ8yKUyM2hbws3ZA7oVeePT_z8UU6HFcfOD9gTkAOxJytyz3gzIBZVGFzvqEkr0&usqp=CAU";
    String mosqueName, mosqueLocation, mosqueKm, mosqueDetails, mosqueAvailableTime, img;
    private int Item_Id = -1;
    ActivityCemeteryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCemeteryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //-----------------------------------------------------------------------------
        mosqueName = binding.hotelName.getText().toString();
        mosqueLocation = binding.locationPin.getText().toString();
        mosqueKm = binding.km.getText().toString();
        mosqueDetails = binding.details.getText().toString();
        mosqueAvailableTime = binding.availableTime.getText().toString();


        Intent intent = getIntent();
        Item_Id = intent.getIntExtra(MosqueFragment.Item_KEY, Item_Id);
        Item_Id = intent.getIntExtra(ChurchesFragment.Item_KEY, Item_Id);

        Bundle extras = getIntent().getExtras();
        if (extras != null) ;
        mosqueName = extras.getString("name");
        mosqueLocation = extras.getString("location");
        mosqueKm = extras.getString("km");
        mosqueDetails = extras.getString("details");
        mosqueAvailableTime = extras.getString("availableTime");
        img = extras.getString("image");

        binding.hotelName.setText(mosqueName);
        binding.locationPin.setText(mosqueLocation);
        binding.km.setText(mosqueKm);
        binding.details.setText(mosqueDetails);
        binding.availableTime.setText(mosqueAvailableTime);

        Glide.with(this).load(img).into(binding.churchesImage);
//--------------------------------------------------------------------------------
        binding.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BottomNavigationBarActivity.class);
                startActivity(intent);
            }
        });

        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "0568711400";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        RequestOptions requestOptions = new RequestOptions().
                transform(new FitCenter(), new RoundedCorners(20));
        Glide.with(this).load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()

                .into(binding.imageView3);

        Glide.with(this).load(url1)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(binding.imageView3);

        Glide.with(this).load(url2)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(binding.imageView3);
    }
}