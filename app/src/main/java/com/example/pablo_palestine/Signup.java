package com.example.pablo_palestine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.databinding.ActivitySignupBinding;
import com.example.pablo_palestine.test.BottomNavigationBarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {

    public static final String PREF_NAME = "RegisterPrefrences";
    ActivitySignupBinding binding;
    String Input_Name, Input_Email, Input_Address, Input_Password;
    FirebaseAuth auth;
    public static final String FullNameKey = "FullName_K", EmailKey = "Email_K", PassKey = "Pass_K", AddressKey = "Address_K";
    public static SharedPreferences SP;    // to read from SharedPreferences
    public static SharedPreferences.Editor EDIT; // to write in / edit SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SP = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        EDIT = SP.edit();

        auth = FirebaseAuth.getInstance();

        // -------------------------- If User is Already signed up Toast method ------------------------------
        restoreResult();


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Input_Name = binding.fullName.getText().toString();
                Input_Email = binding.email.getText().toString();
                Input_Password = binding.password.getText().toString();
                Input_Address = binding.address.getText().toString();

                EDIT.putString(FullNameKey, Input_Name);
                EDIT.putString(EmailKey, Input_Email);
                EDIT.putString(PassKey, Input_Password);
                EDIT.putString(AddressKey, Input_Address);
                EDIT.apply();

                if (!Input_Name.isEmpty() && !Input_Email.isEmpty() && !Input_Password.isEmpty() && !Input_Address.isEmpty()) {

                    auth.createUserWithEmailAndPassword(Input_Email, Input_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                //save user name
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(Input_Name).build();
                                firebaseUser.updateProfile(profileUpdates);

                                Intent intent = new Intent(getBaseContext(), BottomNavigationBarActivity.class);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), "Welcome " + Input_Name, Toast.LENGTH_LONG).show();
                                Animatoo.animateCard(Signup.this);


                            } else {
                                Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("error", task.getException().toString());
                            }

                        }
                    })
                    ;
                } else {
                    Toast.makeText(getBaseContext(), "Empty", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void restoreResult() {
        String Full = SP.getString(FullNameKey, "");
        String Email = SP.getString(EmailKey, "");
        String address = SP.getString(AddressKey, "");
        String Pass = SP.getString(PassKey, "");


        if (Full == null && address == null && Email == null && Pass == null) {
            Toast.makeText(getApplicationContext(), " you Didn't sign up yet ", Toast.LENGTH_SHORT).show();
        } else if (Full != null && Email != null && Pass != null && address != null) {
            Toast.makeText(getApplicationContext(), "you already signed up ", Toast.LENGTH_SHORT).show();
        }
    }

}