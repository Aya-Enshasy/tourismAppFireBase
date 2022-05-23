package com.example.pablo_palestine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pablo_palestine.databinding.ActivityPeopleExperienceBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.hotel_room.Room;
import com.example.pablo_palestine.hotel_room.RoomAdapter;
import com.example.pablo_palestine.hotel_room.RoomsActivity;
import com.example.pablo_palestine.hotels.MyInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PeopleExperience extends AppCompatActivity {
    PostAdapter adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    ActivityPeopleExperienceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPeopleExperienceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerview2.setLayoutManager(layoutManager);


        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PeopleExperience.this,AddPost.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        binding.recyclerview2.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getlist();

    }

    private void getlist(){
        CollectionReference collectionReference = db.collection("users");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialog.dismiss();
                if (!queryDocumentSnapshots.isEmpty()) {
                ArrayList<Posts>  posts = new ArrayList<>();

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Posts taskItem = snapshot.toObject(Posts.class);
                        posts.add(taskItem);
                        adapter = new PostAdapter(posts,PeopleExperience.this);
                        adapter.notifyDataSetChanged();
                    }

                    binding.recyclerview2.setAdapter(adapter);
                }


            }
        });
    }


}
