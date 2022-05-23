package com.example.pablo_palestine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pablo_palestine.churches.ChurchesAdapter;
import com.example.pablo_palestine.databinding.MosqueCherchesDesignBinding;
import com.example.pablo_palestine.databinding.PostsBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    List<Posts> list;
    Context context;


    public PostAdapter(List<Posts> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PostsBinding binding = PostsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.binding.date.setText(list.get(position).getData()+" ,"+list.get(position).getTime());
        holder.binding.textView18.setText(list.get(position).getComment());
        holder.binding.hotelName.setText(list.get(position).getName());

            Glide.with(context).load(list.get(position).getImage()).circleCrop()
                    .apply(new RequestOptions().transform(new RoundedCorners(8))
                            .error(R.drawable.applogo_background)
                            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into((holder).binding.imageView11);

            Glide.with(context).load(list.get(position).getUser_image()).circleCrop()
                    .apply(new RequestOptions().transform(new RoundedCorners(50))
                            .error(R.drawable.applogo_background)
                            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into((holder).binding.photo);


        if (list.get(position).getComment() == null){
            holder.binding.textView18.setVisibility(View.GONE);
        }else if (list.get(position).getImage() == null){
            holder.binding.imageView11.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        PostsBinding binding;

        public MyViewHolder( PostsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

