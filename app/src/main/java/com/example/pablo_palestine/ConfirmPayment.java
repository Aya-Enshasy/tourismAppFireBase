package com.example.pablo_palestine;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.pablo_palestine.databinding.ActivityConfirmPaymentBinding;
import com.example.pablo_palestine.databinding.ActivityRoomsBinding;
import com.example.pablo_palestine.hotel_room.RoomsActivity;

import java.util.Calendar;
import java.util.Locale;

public class ConfirmPayment extends AppCompatActivity {
    int sum = 0;
    String roomName, roomPrice, pr_num, images, tot;
    private int Item_Id = -1;
    SimpleDateFormat sdf;
    ActivityConfirmPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//-------------------------------- Open calender to choose date when click on edittext -------------------------------------
        final Calendar myCalendar = Calendar.getInstance();
        final Calendar myCalendar1 = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth + sum);

                String myFormat = "MMM dd, ''yyyy"; //In which you need put here

                sdf = new SimpleDateFormat(myFormat, Locale.US);

                binding.chikinDate.setText(sdf.format(myCalendar.getTime()));

                binding.chikoutDate.setText(sdf.format(myCalendar1.getTime()));
            }
        };

        binding.chikinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ConfirmPayment.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        binding.chikinDate.setVisibility(View.INVISIBLE);


//----------------****----------------------------------------------------
        binding.constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), VisaCard.class);
                intent.putExtra("total", tot);
                startActivity(intent);
                Animatoo.animateCard(ConfirmPayment.this);

            }
        });
        binding.constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), VisaCard.class);
                intent.putExtra("total", tot);
                startActivity(intent);
                Animatoo.animateCard(ConfirmPayment.this);
            }
        });
//----------------****----------------------------------------------------

        roomName = binding.hotelName.getText().toString();
        pr_num = binding.personNum.getText().toString();

        Intent intent = getIntent();
        Item_Id = intent.getIntExtra(RoomsActivity.Item_KEY, Item_Id);

        Bundle extras = getIntent().getExtras();
        if (extras != null) ;
        roomName = extras.getString("roomName");
        roomPrice = extras.getString("roomPrice");
        pr_num = extras.getString("roomNum");
        images = extras.getString("image1");

        binding.hotelName.setText(roomName);
        binding.priceHotel.setText(roomPrice);
        binding.personNum.setText(pr_num);
        Glide.with(this).load(images).into(binding.imageView6);

//----------------****-----------------------------------------------------


        binding.add.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                sum++;

                binding.dayCount.setText(sum + "");
                binding.totalMoney.setText(totalprice() + "");
                binding.chikinDate.setVisibility(View.VISIBLE);


            }
        });

        binding.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum--;
                if (sum < 1) {
                    sum = 1;
                    return;
                }
                binding.dayCount.setText(sum + "");
                binding.totalMoney.setText(totalprice() + "");


            }
        });

    }

    public int totalprice() {
        int Price = Integer.parseInt((binding.priceHotel.getText().toString()));
        int Count = Integer.parseInt((binding.dayCount.getText().toString()));
        tot = String.valueOf(Count * Price);
        return Integer.parseInt((tot));
    }


}