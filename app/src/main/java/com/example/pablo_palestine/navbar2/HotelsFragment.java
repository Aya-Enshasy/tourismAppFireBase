package com.example.pablo_palestine.navbar2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pablo_palestine.HotelDetails;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.FragmentChurchesBinding;
import com.example.pablo_palestine.databinding.FragmentHotelsBinding;
import com.example.pablo_palestine.hotels.Hotels;
import com.example.pablo_palestine.hotels.HotelsAdapter;
import com.example.pablo_palestine.hotels.MyInterface;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.graph.SuccessorsFunction;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.Utils;

import java.util.ArrayList;
import java.util.List;

public class HotelsFragment extends Fragment {
    ArrayList<Hotels> testItemList  ;
    ProgressDialog progressDialog;
    HotelsAdapter adapter;
    public static final String Item_KEY = "hotel_key";
    DatabaseReference databaseReference;
    FragmentHotelsBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HotelsFragment() {
    }

    public static HotelsFragment newInstance() {
        HotelsFragment fragment = new HotelsFragment();
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
        binding = FragmentHotelsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        testItemList = new ArrayList<>() ;

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL,false);


        binding.recyclerview.setLayoutManager(layoutManager);

        //*********************************************************************
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("Hotels");
        scoresRef.keepSynced(true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Hotels");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.dismiss();
                if (getActivity() == null) {
                    return;
                }
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Hotels hotels = dataSnapshot.getValue(Hotels.class);
                    // الكي اشي يونيك بنشأو الفيربيز اوتوميتيك وبكون بتكررش
                    hotels.setKey(dataSnapshot.getKey());
                    testItemList.add(hotels);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"nodata",Toast.LENGTH_LONG).show();
                Log.e("error","error");
            }
        });



        adapter = new HotelsAdapter(getActivity(), testItemList, new HotelsAdapter.HotelInterface() {
            @Override
            public void onItemClick(Hotels hotels) {
                Intent intent = new Intent(getActivity(), HotelDetails.class);
                intent.putExtra(Item_KEY, hotels);
                startActivity(intent);
            }
        });
        binding.recyclerview.setAdapter(adapter);



        return view;

    }











}