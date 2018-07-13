package com.codepath.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {
    ArrayList<Post> mPosts;
    PostAdapter adapter;
     RecyclerView rvHomepage;
    SwipeRefreshLayout swipeContainer;
    //resolve the recycler view
    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Feed", "refresh successful");
        //loads the top posts on resume to fragment
        loadTopPost();
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mPosts = new ArrayList<>();
        rvHomepage = view.findViewById(R.id.rvPosts);
        adapter = new PostAdapter(mPosts);
        rvHomepage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHomepage.setAdapter(adapter);
        loadTopPost();
        }

    public void fetchTimelineAsync(int page) {
        final Post.Query postQuery = new Post.Query();
        postQuery.orderByDescending("updatedAt");
        postQuery.getTop().withUser();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    adapter.clear();
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "]=" + objects.get(i).getDescription() + "\nusername = " + objects.get(i).getUser().getUsername());
                        Post post = objects.get(i);
                        mPosts.add(post);
                        adapter.notifyItemInserted(mPosts.size() - 1);
                    }
                }
                else {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
            }
        });


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
                        adapter.notifyItemInserted(mPosts.size() - 1);
                    }
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }
}
