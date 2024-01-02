package com.block.imageapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.imageapp.R;
import com.block.imageapp.model.Post;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> postArrayList;

    public PostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postArrayList.get(position);

        holder.txtTitle.setText(post.title);
        holder.txtId.setText(post.id+"");
        holder.txtAlbumId.setText(post.albumId+"");

        Glide.with(context)
                .load(post.thumbnailUrl)
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtId;
        TextView txtAlbumId;
        ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtId = itemView.findViewById(R.id.txtId);
            txtAlbumId = itemView.findViewById(R.id.txtAlbumId);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }
}
