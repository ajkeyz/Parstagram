package com.codepath.parstagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parstagram.model.Post;
import com.parse.ParseFile;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {



    ArrayList<Post> posts;
    Context context;
    Bitmap.Config config;


    //intitialize list of posts
    public PostAdapter(ArrayList<Post> posts){
        this.posts = posts;
    }

    public Bitmap.Config getConfig() {
        return config;
    }

    public void setConfig(Bitmap.Config config){
        this.config = config;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.itempost, parent, false);

        //return new Viewholder

        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the post data at the specified position
        Post post = posts.get(position);
        holder.etDescription.setText(post.getUser().getUsername() + " : " + post.getDescription());
        holder.userName.setText(post.getUser().getUsername());

        ParseFile img = post.getImage();
        String imgUrl = "";
        if (img != null){
            imgUrl = img.getUrl();
        }

        Glide.with(context)
                .load(imgUrl)
                .into(holder.userImage);




    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView userImage;
        TextView etDescription;
        TextView userName;



        public ViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.homeImage);
            etDescription = itemView.findViewById(R.id.homeDescription);
            userName = itemView.findViewById(R.id.homeUsername);

        }


    }
}
