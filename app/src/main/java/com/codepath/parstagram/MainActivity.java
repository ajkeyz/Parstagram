package com.codepath.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button btLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {

            setContentView(R.layout.activity_main);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            usernameInput = findViewById(R.id.usernameInput);
            passwordInput = findViewById(R.id.passwordInput);
            btLogIn = findViewById(R.id.btLogIn);


            btLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //fetch the user's input
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    //feed them into the login method
                    login(username, password);

                }
            });
        }
        else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login(String username, String password){
        //setup parse configuration
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            //to get info about our login request
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("LoginActivity", "Login Successful!");
                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }

            }
        });



        BottomNavigationView abc = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        abc.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_button:
                        // do something here
                        return true;
                    case R.id.home_page:
                        // do something here
                        return true;
                    case R.id.add_post:
                        // do something here
                        return true;
                }
            }
        });



    }

    public void signUp(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }





}
