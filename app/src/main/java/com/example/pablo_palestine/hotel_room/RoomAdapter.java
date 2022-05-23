package com.example.pablo_palestine.hotel_room;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo_palestine.ChurchesDetails;
import com.example.pablo_palestine.ConfirmPayment;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.Signup;
import com.example.pablo_palestine.churches.ChurchesAdapter;
import com.example.pablo_palestine.databinding.MosqueCherchesDesignBinding;
import com.example.pablo_palestine.databinding.RoomDetaliesBinding;
import com.example.pablo_palestine.hotels.MyInterface;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private final  ArrayList<Room> list  ;
    Context context;
    private static MyInterface listener ;
    private int Item_Id =-1;
    public RoomAdapter(Context context, ArrayList<Room> list, MyInterface listener){
        this.list = list;
        this.context= context;
        this.listener=listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RoomDetaliesBinding binding = RoomDetaliesBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RoomAdapter.ViewHolder holder, int position) {

        holder.binding.room.setText(list.get(position).getRoomName());
        holder.binding.roomNum.setText(list.get(position).getRoomNum());
        holder.binding.roomPrice.setText(list.get(position).getRoomPrice());

        Glide.with(context).load(list.get(position).getImage1())
                .transition(withCrossFade())
                .circleCrop()
                .apply(new RequestOptions().transform(new RoundedCorners(20))
                        .error(R.drawable.applogo_background).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into((holder).binding.img1);

        Glide.with(context).load(list.get(position).getImage2())
                .transition(withCrossFade())
                .circleCrop()
                .apply(new RequestOptions().transform(new RoundedCorners(20))
                        .error(R.drawable.applogo_background).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into((holder).binding.img2);

        Glide.with(context).load(list.get(position).getImage3())
                .transition(withCrossFade())
                .circleCrop()
                .apply(new RequestOptions().transform(new RoundedCorners(20))
                        .error(R.drawable.applogo_background).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into((holder).binding.img3);

        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ConfirmPayment.class);
                intent.putExtra("roomName", list.get(position).getRoomName());
                intent.putExtra("roomPrice", list.get(position).getRoomPrice());
                intent.putExtra("roomNum", list.get(position).getRoomNum());
                intent.putExtra("image1", list.get(position).getImage1());
                v.getContext().startActivity(intent);
                Animatoo.animateCard(context);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
      RoomDetaliesBinding binding;
        public ViewHolder(RoomDetaliesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


    }

}
