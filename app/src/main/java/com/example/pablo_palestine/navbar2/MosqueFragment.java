package com.example.pablo_palestine.navbar2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pablo_palestine.ChurchesDetails;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.FragmentChurchesBinding;
import com.example.pablo_palestine.databinding.FragmentMosqueBinding;
import com.example.pablo_palestine.hotels.MyInterface;
import com.example.pablo_palestine.mosque.Mosque;
import com.example.pablo_palestine.mosque.MosqueAdapter;
import com.example.pablo_palestine.navbar.ArchaeologicalFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class MosqueFragment extends Fragment {
    DatabaseReference databaseReference;
    ArrayList<Mosque> _mosques;
    MosqueAdapter adapter;
    ProgressDialog progressDialog;
    FragmentMosqueBinding binding;
    public static final String Item_KEY = "churches_key";



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MosqueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment MosqueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MosqueFragment newInstance() {
        MosqueFragment fragment = new MosqueFragment();
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
        binding = FragmentMosqueBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        _mosques = new ArrayList<>() ;

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),
                RecyclerView.HORIZONTAL,false);
        binding.recyclerview.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Mosque");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Mosque mosque = dataSnapshot.getValue(Mosque.class);
                    _mosques.add(mosque);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"nodata",Toast.LENGTH_LONG).show();
                Log.e("error","error");
            }
        });

        adapter = new MosqueAdapter(getActivity(), _mosques, new MyInterface() {
            @Override
            public void onItemClick(int Id) {
                Intent intent = new Intent(getActivity(), ChurchesDetails.class);
                intent.putExtra(Item_KEY, Id);
                startActivity(intent);
            }
        });


        binding.recyclerview.setAdapter(adapter);

        return view;

    }


}