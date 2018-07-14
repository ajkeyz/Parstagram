package com.codepath.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    RecyclerView rvProfile;
    ArrayList<Post> mPosts;
    ProfileAdapter profileAdapter;
    Button btLogOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProfile = view.findViewById(R.id.rvProfile);
        btLogOut = view.findViewById(R.id.btLogOut);
        mPosts = new ArrayList<>();
        profileAdapter = new ProfileAdapter(mPosts);
        rvProfile.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvProfile.setAdapter(profileAdapter);
        loadTopPost();



        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
    }
    public void logOut() {
        ParseUser.logOut();
        Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
        startActivity(intent);

    }
    public void loadTopPost() {
        final Post.Query postQuery = new Post.Query();
        postQuery.orderByDescending("updatedAt");
        postQuery.getTop().withUser();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "]=" + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername());
                        Post post = objects.get(i);
                        mPosts.add(post);
                        profileAdapter.notifyItemInserted(mPosts.size() - 1);
                    }
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }
}


