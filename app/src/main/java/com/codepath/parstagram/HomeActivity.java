package com.codepath.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private static final String imagePath = Environment.getExternalStorageDirectory()+ "/storage/emulated/0/DCIM/Camera/IMG_20180710_135221.jpg";
    EditText etDescription;
    Button create_btn;
    Button refresh_btn;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView userImage;
    Button btLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etDescription = findViewById(R.id.etDescription);
        create_btn = findViewById(R.id.create_btn);
        refresh_btn = findViewById(R.id.refresh_btn);
        userImage = findViewById(R.id.ivCamImage);
        btLogOut = findViewById(R.id.btLogOut);


        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(getApplicationContext().getFilesDir(), "temp.jpg");
                final ParseFile parseFile = new ParseFile(file);
                createPost(description, parseFile, user);
            }
        });



        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPost();
            }
        });



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

                    }
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPost(String description, ParseFile imageFile, ParseUser user){
        //TODO create and save post

        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.d("HomeActivity", "Create Post Success");

                }
                else  {
                    e.printStackTrace();
                    Log.d("HomeActivity", e.getMessage());

                }
            }
        });



    }

    public void logOut(View view){
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);

    }











    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userImage.setImageBitmap(imageBitmap);
            persistImage(imageBitmap, "temp" );
        }
    }

    private void persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }


}
