package com.example.pablo_palestine.my_account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.ActivityNotificationsBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.navbar.AccountFragment;
import com.example.pablo_palestine.test.BottomNavigationBarActivity;

public class Notifications extends AppCompatActivity {

    ActivityNotificationsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
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