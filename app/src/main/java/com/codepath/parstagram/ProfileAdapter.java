
package com.codepath.parstagram;

import android.content.Context;
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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    Context context;
    ArrayList<Post> posts;
    public ProfileAdapter(ArrayList<Post> posts){
        this.posts = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.profile_item, parent, false);
        //return new Viewholder
        return new ViewHolder(postView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);

        ParseFile img = post.getImage();
        String imgUrl = "";
        if (img != null) {
            imgUrl = img.getUrl();
        }
        Glide.with(context)
                .load(imgUrl)
                .into(holder.profileImage);
        }

    @Override
    public int getItemCount() {
        return posts.size();
    }


   public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView profileUsername;
        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profilePic);

     }
 }

}

