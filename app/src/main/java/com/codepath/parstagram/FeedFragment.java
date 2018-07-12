package com.codepath.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private RecyclerView rvHomepage;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPosts = new ArrayList<>();
        rvHomepage = view.findViewById(R.id.rvPosts);
        rvHomepage.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PostAdapter(mPosts);
        rvHomepage.setAdapter(adapter);



        loadTopPost();


    }

    private void loadTopPost() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "]=" + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
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
