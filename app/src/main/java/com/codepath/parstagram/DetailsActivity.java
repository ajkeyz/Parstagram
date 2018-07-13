package com.codepath.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parstagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    Post post;
    ImageView detailImage;
    TextView detailTime;
    TextView detailDescription;
    TextView detailUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        detailImage = findViewById(R.id.detailImage);
        detailDescription = findViewById(R.id.detailDescription);
        detailUsername = findViewById(R.id.detailUsername);
        detailTime = findViewById(R.id.detailTime);
        post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        detailDescription.setText(post.getUser().getUsername() + " : " + post.getDescription());
        detailUsername.setText(post.getUser().getUsername());
        detailTime.setText(post.getRelativeTimeAgo());

        ParseFile img = post.getImage();
        String imgUrl = "";
        if (img != null) {
            imgUrl = img.getUrl();
        }
        Glide.with(this)
                .load(imgUrl)
                .into(detailImage);
    }
}
