package com.example.pablo_palestine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.OnPayBtnClickListner;
import com.example.pablo_palestine.databinding.ActivityVisaCardBinding;

public class VisaCard extends AppCompatActivity {
    String price;
    ActivityVisaCardBinding binding;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisaCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView payment =  findViewById(R.id.payment_amount);
        Button button =  findViewById(R.id.btn_pay);


        button.setText(String.format("payer $s",payment.getText()));
        button.setBackgroundResource(R.drawable.splash_btn);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            price = bundle.getString("total");
            payment.setText(price);
        }

        payment.setTextColor(Color.parseColor("#C3D61B"));
        payment.setTypeface(Typeface.DEFAULT_BOLD);



        binding.cardform.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                //Your code here!! use card.getXXX() for get any card property
                //for instance card.getName();
                Toast.makeText(VisaCard.this, String.format("Exp $d$d",card.getExpMonth(),card.getExpYear()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}