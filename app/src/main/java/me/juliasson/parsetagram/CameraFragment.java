package me.juliasson.parsetagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import me.juliasson.parsetagram.model.Post;

public class CameraFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    ImageView ivPreview;
    Button bvCreate;
    EditText etDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.fragment_camera, parent, false);
        ivPreview = v.findViewById(R.id.ivPreview);
        bvCreate = v.findViewById(R.id.bvCreate);
        etDescription = v.findViewById(R.id.etDescription);



        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
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
                    Log.d("CreatePostActivity", "Create item_post success");
                    Toast.makeText(getActivity(), "Post created!", Toast.LENGTH_SHORT).show();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}