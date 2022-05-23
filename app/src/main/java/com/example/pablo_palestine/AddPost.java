package com.example.pablo_palestine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo_palestine.databinding.ActivityAddPostBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.hotel_room.Room;
import com.example.pablo_palestine.navbar.AccountFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.okhttp.internal.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.pablo_palestine.navbar.AccountFragment.profileImageUrl1;

public class AddPost extends AppCompatActivity {

    ProgressDialog progressDialog;
    StorageReference storageReference;
    public FirebaseUser currentUser;
    public FirebaseAuth mAuth;
    private static final int GALLERY_INTENT = 1000;
    Uri mainImageURI;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference, postRef;
    String saveCurrentData, saveTime, current_user_id,comment1;
    FirebaseFirestore db;
    ActivityAddPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = FirebaseDatabase.getInstance().getReference().child("posts");
        current_user_id = mAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();



        comment1 = binding.comment.getText().toString();
        Calendar calendardate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        saveCurrentData = simpleDateFormat.format(calendardate.getTime());

        Calendar calendartime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormattime = new SimpleDateFormat("HH:mm");
        saveTime = simpleDateFormattime.format(calendartime.getTime());

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

            }
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"), GALLERY_INTENT);
            }
        });

    }

    //------------***--------------------------------------------------
    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storageReference = firebaseStorage.getReference("images/" + currentUser.getUid() + "/" + mainImageURI.getLastPathSegment());

        StorageTask<UploadTask.TaskSnapshot> uploadTask = storageReference.putFile(mainImageURI);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(AddPost.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                //this is the new way to do it
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String profileImageUrl = task.getResult().toString();
                        Glide.with(AddPost.this).load(profileImageUrl).transform(new RoundedCorners(8)).error(R.drawable.applogo_background).into(binding.photo);
                        Log.i("UploadActivity", profileImageUrl);


//**************************************************----------------------------**********************************************
                       //user photo
                        profileImageUrl1=task.getResult().toString();
                        DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());

                        Map<String, Object> map = new HashMap<>();
                        map.put("comment", binding.comment.getText().toString());
                        map.put("Data", saveCurrentData);
                        map.put("Time", saveTime);
                        map.put("name", mAuth.getCurrentUser().getDisplayName());
                        map.put("image", profileImageUrl);
                        map.put("user_image", profileImageUrl1);


                        documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(AddPost.this, "success", Toast.LENGTH_SHORT).show();
                                AddPost.this.finish();
                            }
                        });
//**************************************************----------------------------***********************************
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
                Toast.makeText(AddPost.this, "Image Uploaded Failed!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        progressDialog.setProgress((int) progress);
                        Log.d("UploadActivity", "Upload is " + progress + "% done");
                    }
                });
    }

    //------------****-------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mainImageURI = data.getData();
            if (mainImageURI.getPath() != null)
                Glide.with(AddPost.this).load(mainImageURI).transform(new RoundedCorners(8)).error(R.drawable.applogo_background).into(binding.photo);
        }
    }

//-----------------------------------------------------------------------------


}