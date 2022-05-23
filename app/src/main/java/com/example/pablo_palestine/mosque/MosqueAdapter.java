package com.example.pablo_palestine.mosque;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo_palestine.ChurchesDetails;
import com.example.pablo_palestine.R;
import com.example.pablo_palestine.churches.ChurchesAdapter;
import com.example.pablo_palestine.databinding.MosqueCherchesDesignBinding;
import com.example.pablo_palestine.hotels.MyInterface;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MosqueAdapter extends RecyclerView.Adapter<MosqueAdapter.ViewHolder> {
    private ArrayList<Mosque> list;
    Context context;
    private MyInterface listener ;

    public MosqueAdapter(Context context, ArrayList<Mosque> list, MyInterface listener){
        this.list = list;
        this.context= context;
        this.listener=listener;

    }

    @Override
    public MosqueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MosqueCherchesDesignBinding binding = MosqueCherchesDesignBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MosqueAdapter.ViewHolder holder, int position) {
        holder.binding.MosqueName.setText(list.get(position).getName());
        holder.binding.locationPin.setText(list.get(position).getLocation());
        Glide.with(context).load(list.get(position).getImage())
                .transition(withCrossFade())
                .circleCrop()
                .apply(new RequestOptions().transform(new RoundedCorners(10))
                        .error(R.drawable.applogo_background).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.binding.MosqueImageView);



        //لما يضفط علالايتم
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ChurchesDetails.class);
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("location", list.get(position).getLocation());
                intent.putExtra("details", list.get(position).getDetails());
                intent.putExtra("availableTime", list.get(position).getAvailableTime());
                intent.putExtra("km", list.get(position).getKm());
                intent.putExtra("image", list.get(position).getImage());
                //intent.putExtra("rate", list.get(position).getRate()+"");
                v.getContext().startActivity(intent);
                Animatoo.animateCard(context);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        MosqueCherchesDesignBinding binding;
        public ViewHolder( MosqueCherchesDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
