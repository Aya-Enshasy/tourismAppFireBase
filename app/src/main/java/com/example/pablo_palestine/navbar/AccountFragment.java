package com.example.pablo_palestine.navbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.pablo_palestine.Login;
import com.example.pablo_palestine.PeopleExperience;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.databinding.FragmentAccountBinding;
import com.example.pablo_palestine.databinding.FragmentMapsBinding;
import com.example.pablo_palestine.my_account.About;
import com.example.pablo_palestine.my_account.Notifications;
import com.example.pablo_palestine.my_account.Support;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {

    private static final int GALLERY_INTENT = 1000;
    StorageReference storageReference;
    private ProgressDialog progressDialog;
    public FirebaseUser currentUser;
    public FirebaseAuth mAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Uri mainImageURI;
     FirebaseFirestore db;
    public  static  String profileImageUrl1;
    FirebaseStorage firebaseStorage;
    FragmentAccountBinding binding;



    private String mParam1;
    private String mParam2;
    private ContentResolver contentResolver;

    public AccountFragment() {
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
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

        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        if (currentUser != null) {
            Glide.with(this).load(currentUser.getPhotoUrl()).into(binding.photo);
        }

        binding.camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select file"), GALLERY_INTENT);
            }
        });

        ImageView imageView = view.findViewById(R.id.Notifications);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), Notifications.class);
                startActivity(intent);
                Animatoo.animateCard(getActivity());

            }
        });

        ImageView support = view.findViewById(R.id.Support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent support = new Intent(getActivity(), Support.class);
                startActivity(support);
                Animatoo.animateCard(getActivity());
            }
        });

        ImageView privacy = view.findViewById(R.id.Privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification = new Intent(getActivity(), Notifications.class);
                startActivity(notification);
                Animatoo.animateCard(getActivity());
            }
        });

        ImageView About = view.findViewById(R.id.About);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getActivity(), About.class);
                startActivity(about);
                Animatoo.animateCard(getActivity());
            }
        });

        ImageView Out = view.findViewById(R.id.Out);
        Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();

            }
        });

        ImageView share = view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(getActivity(), PeopleExperience.class);
                startActivity(about);
                Animatoo.animateCard(getActivity());

            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mainImageURI = data.getData();

            if (mainImageURI.getPath() != null)
                updateProfile();
                uploadImage();
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent support = new Intent(getActivity(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().finish();
        support.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        support.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(support);
        Animatoo.animateInAndOut(getActivity());
    }

    private void updateProfile() {

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setPhotoUri(mainImageURI)
                .build();
        currentUser.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            currentUser.reload();
                            Glide.with(getActivity()).load(currentUser.getPhotoUrl()).into(binding.photo);

                            Toast.makeText(getActivity(), "Profile Update for", Toast.LENGTH_SHORT).show();
                        } else {
                            task.getException().printStackTrace();
                            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //---------------------------start storage------------------------------------
    private void uploadImage() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storageReference = firebaseStorage.getReference("images/" + currentUser.getUid() + "/" + mainImageURI.getLastPathSegment());

        StorageTask<UploadTask.TaskSnapshot> uploadTask = storageReference.putFile(mainImageURI);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                //this is the new way to do it
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public  void onComplete(@NonNull Task<Uri> task) {
                        profileImageUrl1 = task.getResult().toString();
                        Glide.with(getActivity()).load(profileImageUrl1).transform(new RoundedCorners(8)).error(R.drawable.applogo_background).into(binding.photo);
                        Log.i("UploadActivity", profileImageUrl1);


//**************************************************----------------------------**********************************************
                        //firestore
                        DocumentReference documentReference = db.collection("usersProfile").document(mAuth.getCurrentUser().getUid());
                        Map<String, Object> map = new HashMap<>();
                        map.put("user_image", profileImageUrl1);

                        documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Image Uploaded Failed!!", Toast.LENGTH_SHORT).show();
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
    //-----------------------end storage------------------------------------------
}