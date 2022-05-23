package com.example.pablo_palestine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.pablo_palestine.databinding.ActivityLoginBinding;
import com.example.pablo_palestine.test.BottomNavigationBarActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_str=binding.email.getText().toString();
                String password_str=binding.password.getText().toString();

                if (!email_str.isEmpty() && !password_str.isEmpty()) {
                    auth.signInWithEmailAndPassword(email_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Welcome" + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getBaseContext(), BottomNavigationBarActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                Log.e("error", task.getException().toString());
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                else{
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.email));
                    YoYo.with(Techniques.Tada).duration(500).repeat(1).playOn(findViewById(R.id.password));
                    Toast.makeText(getBaseContext(), "Please check your Email Field and Password Field", Toast.LENGTH_SHORT).show();

                }


            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Signup.class);
                startActivity(i);
            }
        });
    }


}