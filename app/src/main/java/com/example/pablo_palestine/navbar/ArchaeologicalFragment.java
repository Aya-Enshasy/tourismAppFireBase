package com.example.pablo_palestine.navbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.FragmentArchaeologicalBinding;
import com.example.pablo_palestine.databinding.FragmentMapsBinding;
import com.example.pablo_palestine.navbar2.ChurchesFragment;
import com.example.pablo_palestine.navbar2.HotelsFragment;
import com.example.pablo_palestine.navbar2.MosqueFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class ArchaeologicalFragment extends Fragment {
    FragmentArchaeologicalBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ArchaeologicalFragment() {
    }

    public static ArchaeologicalFragment newInstance() {
        ArchaeologicalFragment fragment = new ArchaeologicalFragment();
        Bundle args = new Bundle();

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

        binding = FragmentArchaeologicalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.topNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Hotels) {
                    openFragment(HotelsFragment.newInstance());
                } else if (item.getItemId() == R.id.Mosque) {
                    openFragment(MosqueFragment.newInstance());
                } else {
                    openFragment(ChurchesFragment.newInstance());
                }
                return true;
            }
        });
        openFragment(HotelsFragment.newInstance());
        return view;

    }

    void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.viewpager, fragment);
        fragmentTransaction.commit();
    }

    }
