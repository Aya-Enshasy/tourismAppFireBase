package com.example.pablo_palestine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pablo_palestine.databinding.ActivityMainBinding;
import com.example.pablo_palestine.onbording.OnboardingActivity;
import com.example.pablo_palestine.test.BottomNavigationBarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    public static SharedPreferences onboarding;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


    // -------------------------- If User is Already signed up Toast method ------------------------------

        binding.getstsrted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if first time
                onboarding = getSharedPreferences("onboarding", MODE_PRIVATE);

                boolean isFirsttime = onboarding.getBoolean("firsttime", true);

                if (isFirsttime) {
                    SharedPreferences.Editor EDIT = onboarding.edit();
                    EDIT.putBoolean("firsttime", false);
                    EDIT.commit();
                    Intent i = new Intent(getBaseContext(), OnboardingActivity.class);
                    startActivity(i);

                } else if (!isFirsttime && currentUser != null) {
                    Intent i = new Intent(getBaseContext(), BottomNavigationBarActivity.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent(getBaseContext(), Login.class);
                    startActivity(intent);
                }
            }
        });

    }


}