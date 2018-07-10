package me.juliasson.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import me.juliasson.parsetagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    private EditText etDescription;
    private Button bvCreate;
    private Button bvRefresh;
    private Button bvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.tbToolBar);
        setSupportActionBar(toolbar);

        etDescription = findViewById(R.id.etDescription);
        bvCreate = findViewById(R.id.bvCreate);
        bvRefresh = findViewById(R.id.bvRefresh);
        bvSettings = findViewById(R.id.bvSettings);

        bvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                final File image = new File("/storage/emulated/0/DCIM/Restored/20180704_102825.jpg");
                final ParseFile parseFile = new ParseFile(image);

                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            createPost(description, parseFile, user);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        bvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });

        bvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        loadTopPosts();
    }

    private void createPost(String description, ParseFile image, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(image);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadTopPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername: " + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
