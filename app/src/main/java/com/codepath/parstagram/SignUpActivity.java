package com.codepath.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {


    private EditText signUpUsername;
    private EditText signUpPassword;
    private EditText emailInput;
    private EditText phoneInput;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
       // ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();

        signUpUsername = findViewById(R.id.signUpUsername);
        signUpPassword = findViewById(R.id.signUpPassword);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = signUpUsername.getText().toString();
                final String password = signUpPassword.getText().toString();
                final String email = emailInput.getText().toString();
                final String phoneNumber = phoneInput.getText().toString();

                //feed them into the sign up method
                signUpUser(username, password, email, phoneNumber);

            }
        });
    }


    public void signUpUser(String username, String password, String email, String phoneNo){
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        user.put("phone",phoneNo);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done (ParseException e){
                if (e == null) {
                    Log.d("LoginActivity", "Sign Up Success!");
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e("LoginActivity", "Sign Up failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
