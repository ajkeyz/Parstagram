package com.codepath.parstagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.parstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class PostFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    EditText etDescription;
    Button create_btn;
    Button refresh_btn;
    ImageView userImage;
    Button btLogOut;
    Button btTakePhoto;
    String mCurrentPhotoPath;
    SwipeRefreshLayout swipeContainer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        etDescription = view.findViewById(R.id.etDescription);
        create_btn = view.findViewById(R.id.create_btn);
        //makes button unclickable until a post is added
        create_btn.setEnabled(false);
        refresh_btn = view.findViewById(R.id.refresh_btn);
        userImage = view.findViewById(R.id.ivCamImage);
        btLogOut = view.findViewById(R.id.btLogOut);
        btTakePhoto = view.findViewById(R.id.btTakePhoto);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(mCurrentPhotoPath);
                final ParseFile parseFile = new ParseFile(file);
                // ((HomeActivity)getActivity()).changeHome();
                createPost(description, parseFile, user);
            }
        });

        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });


    }
    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        //TODO create and save post

        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("PostFragment", "Create Post Success");
                    ((HomeActivity) getActivity()).changeHome();
                } else {
                    e.printStackTrace();
                    Log.d("HomeActivity", e.getMessage());
                    }
            }
        });


    }

    public void logOut() {
        ParseUser.logOut();
        Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
        startActivity(intent);

    }
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //Create the file where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("PostFragment", "File was successfully created");
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("PostFragment", "Error with image");
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.codepath.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //enable button click after post must have been created
            create_btn.setEnabled(true);
            userImage.setImageURI(Uri.parse(mCurrentPhotoPath));
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        // getExternalFilesDir() + "/Pictures" should match the declaration in fileprovider.xml paths
        File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
        // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
        Uri bmpUri;
        bmpUri = FileProvider.getUriForFile(getActivity(), "com.codepath.fileprovider", file);
        //Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
        }
}
