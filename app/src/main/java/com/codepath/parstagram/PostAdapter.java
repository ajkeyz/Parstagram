package com.codepath.parstagram;

import android.content.Context;
import android.content.Intent;
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

import org.parceler.Parcels;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    ArrayList<Post> posts;
    Context context;
    //intitialize list of posts
    public PostAdapter(ArrayList<Post> posts) {
        this.posts = posts;
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
        holder.timeStamp.setText(post.getRelativeTimeAgo());

        ParseFile img = post.getImage();
        String imgUrl = "";
        if (img != null) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView userImage;
        TextView etDescription;
        TextView userName;
        TextView timeStamp;


        public ViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.homeImage);
            etDescription = itemView.findViewById(R.id.homeDescription);
            userName = itemView.findViewById(R.id.homeUsername);
            timeStamp = itemView.findViewById(R.id.tvTimeStamp);

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            //get item position
            int position = getAdapterPosition();
            //make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                //get the post at the position, this wont work if the class is static
                Post post = posts.get(position);
                //create an intent for the activity
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("post", Parcels.wrap(post));
                //show the activity
                context.startActivity(intent);
                }
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

}


