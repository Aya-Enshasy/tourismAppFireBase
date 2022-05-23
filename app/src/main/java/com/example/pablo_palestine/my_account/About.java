package com.example.pablo_palestine.my_account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.ActivityAboutBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.navbar.AccountFragment;
import com.example.pablo_palestine.test.BottomNavigationBarActivity;

public class About extends AppCompatActivity {

    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BottomNavigationBarActivity.class);
                startActivity(intent);
            }
        });
    }
}