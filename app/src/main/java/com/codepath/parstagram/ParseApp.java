package com.codepath.parstagram;

import android.app.Application;

import com.codepath.parstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("anjolaomoniyi")
                .clientKey("Mobolaji99?")
                .server("http://parstagram-ajj.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
