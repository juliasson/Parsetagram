package me.juliasson.parsetagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.juliasson.parsetagram.model.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parsetagram")
                .clientKey("D1515m3hpa55w0rD")
                .server("http://juliasson-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
