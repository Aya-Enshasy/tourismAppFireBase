package com.example.pablo_palestine.hotels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.churches.ChurchesAdapter;
import com.example.pablo_palestine.databinding.HotelsBinding;
import com.example.pablo_palestine.databinding.MosqueCherchesDesignBinding;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.myviewholder> {

    Context context;
    private  ArrayList<Hotels> list ;
    private HotelInterface listener ;

    public HotelsAdapter(Context context, ArrayList<Hotels> list, HotelInterface listener) {
        this.context=context;
        this.list=list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public HotelsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HotelsBinding binding = HotelsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new myviewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelsAdapter.myviewholder holder, int position) {
        final Hotels hotels = list.get(position);
        if(hotels==null)
            return;
        holder.binding.HotelNo.setText(hotels.getName());
        holder.binding.rate.setText(hotels.getRate()+"");
        holder.binding.locationPin.setText(hotels.getLocation());

        if (context  != null) {
            Glide.with(context).load(hotels.getImage())
                    .transition(withCrossFade())
                    .circleCrop()
                    .apply(new RequestOptions().transform(new RoundedCorners(20))
                            .error(R.drawable.applogo_background).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into((holder).binding.image1);
        }
        //لما يضفط علالايتم
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(hotels);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    public class myviewholder extends RecyclerView.ViewHolder{
        HotelsBinding  binding;
        public myviewholder(HotelsBinding  binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface HotelInterface {

        void onItemClick(Hotels hotels);
    }
}
