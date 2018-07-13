package com.codepath.parstagram;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toolbar;


public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    MenuView.ItemView itemView;
    Button create_btn;
    SwipeRefreshLayout swipeContainer;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Fragment fragment1 = new FeedFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.your_placeholder, fragment1).commit();
        itemView = findViewById(R.id.home_page);
        Drawable me = getApplicationContext().getResources().getDrawable(R.drawable.instagram_home_filled_24);
        itemView.setIcon(me);
        // define your fragments here
        final Fragment fragment2 = new PostFragment();
        final Fragment fragment3 = new ProfileFragment();
        // handle navigation selection
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_page:
                                itemView = findViewById(R.id.home_page);
                                Drawable me = getApplicationContext().getResources().getDrawable(R.drawable.instagram_home_filled_24);
                                item.setIcon(me);
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.your_placeholder, fragment1).commit();
                                return true;
                            case R.id.add_post:
                                itemView = findViewById(R.id.add_post);
                                Drawable you = getApplicationContext().getResources().getDrawable(R.drawable.instagram_new_post_filled_24);
                                item.setIcon(you);
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.your_placeholder, fragment2).commit();
                                return true;
                            case R.id.profile:
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.your_placeholder, fragment3).commit();
                                return true;
                        }
                        return false;
                    }
                });
    }
    public void changeHome() {
        bottomNavigationView.setSelectedItemId(R.id.home_page);
    }
}
