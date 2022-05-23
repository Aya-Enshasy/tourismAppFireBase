package com.example.pablo_palestine.hotel_room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pablo_palestine.ConfirmPayment;
import com.example.pablo_palestine.R;

import com.example.pablo_palestine.databinding.ActivityLoginBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.hotels.Hotels;
import com.example.pablo_palestine.hotels.MyInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ArrayList<Room> rooms =new ArrayList<>();
    RoomAdapter adapter;
    public static final String Item_KEY = "hotel_key";
    private String key;
    ActivityRoomsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        key = intent.getStringExtra("room");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerview.setLayoutManager(layoutManager);

        adapter = new RoomAdapter(RoomsActivity.this, rooms, new MyInterface() {
            @Override
            public void onItemClick(int Id) {
                Intent intent = new Intent(getBaseContext(), ConfirmPayment.class);
                intent.putExtra(Item_KEY, Id);
                startActivity(intent);
            }
        });
        binding.recyclerview.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hotels").child(key).child("room");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Room room = dataSnapshot.getValue(Room.class);
                        rooms.add(room);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No circle", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "nodata", Toast.LENGTH_LONG).show();
                Log.e("error", "error");
            }
        });

        adapter.notifyDataSetChanged();

    }




}



