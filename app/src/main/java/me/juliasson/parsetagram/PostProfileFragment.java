package me.juliasson.parsetagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import me.juliasson.parsetagram.model.Post;

public class PostProfileFragment extends Fragment {

    ImageView ivProfileImageDetail;
    TextView tvUserName;
    TextView tvNumPosts;
    RecyclerView rvProfilePosts;
    ArrayList<Post> profilePosts;
    PostAdapter postAdapter;
    int counter=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Post post = getArguments().getParcelable("post");

        ivProfileImageDetail = view.findViewById(R.id.ivProfileImageDetail);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvNumPosts = view.findViewById(R.id.tvNumPosts);

        tvUserName.setText(post.getUser().getUsername());

        Glide.with(view.getContext())
                .load(post.getUser().getParseFile("profileImage").getUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfileImageDetail);

        rvProfilePosts = ((RecyclerView)view.findViewById(R.id.rvProfilePosts));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.direct_gallery_grid_spacing);
        rvProfilePosts.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        profilePosts = new ArrayList<>();
        postAdapter = new PostAdapter(profilePosts);
        rvProfilePosts.setAdapter(postAdapter);
        rvProfilePosts.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadProfilePosts(post.getUser());
    }

    private void loadProfilePosts(final ParseUser postUser) {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < objects.size(); i++) {
                        Post post = objects.get(i);
                        String currentUserId = postUser.getObjectId();
                        String postUserId = post.getUser().getObjectId();
                        if (currentUserId.equals(postUserId)) {
                            profilePosts.add(0, post);
                            postAdapter.notifyItemInserted(profilePosts.size() - 1);
                            counter++;
                        }
                    }
                    tvNumPosts.setText(String.format("%s", counter));
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
