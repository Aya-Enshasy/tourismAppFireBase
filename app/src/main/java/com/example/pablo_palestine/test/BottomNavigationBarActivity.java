package com.example.pablo_palestine.test;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pablo_palestine.R;
import com.example.pablo_palestine.navbar.AccountFragment;
import com.example.pablo_palestine.navbar.MapsFragment;
import com.example.pablo_palestine.navbar.ArchaeologicalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Home) {
                    openFragment(MapsFragment.newInstance());
                } else if (item.getItemId() == R.id.Orders) {
                    openFragment(ArchaeologicalFragment.newInstance());
                } else {
                    openFragment(AccountFragment.newInstance());
                }
                return true;
            }
        });
        openFragment(MapsFragment.newInstance());
    }
     void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.viewpager1, fragment);
        fragmentTransaction.commit();
    }

}