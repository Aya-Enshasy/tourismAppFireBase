package com.example.pablo_palestine.onbording;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pablo_palestine.Login;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.ActivityAddPostBinding;
import com.example.pablo_palestine.databinding.ActivityOnboardingBinding;


public class OnboardingActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    ActivityOnboardingBinding binding;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeStatusbarTransparent();


        onboardingAdapter = new OnboardingAdapter(this);
        binding.onboardingViewPager.setAdapter(onboardingAdapter);
        binding.onboardingViewPager.setPageTransformer(false, new OnboardingPageTransformer());


    }


    public void nextPage(android.view.View view) {
        if (view.getId() == R.id.skip) {
            if (binding.onboardingViewPager.getCurrentItem() < onboardingAdapter.getCount() - 1) {
                binding.onboardingViewPager.setCurrentItem(binding.onboardingViewPager.getCurrentItem() + 1, true);
            }
            findViewById(R.id.skip1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent i = new Intent(getBaseContext(), Login.class);
                    startActivity(i);
                }
            });
        }
    }

    private void makeStatusbarTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
        }
    }
}
