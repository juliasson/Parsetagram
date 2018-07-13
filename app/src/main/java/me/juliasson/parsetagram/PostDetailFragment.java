package me.juliasson.parsetagram;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.juliasson.parsetagram.model.Post;

public class PostDetailFragment extends Fragment {

    FragmentActivity listener;

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_post_detail, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        Post post;

        ImageView ivProfileImage = view.findViewById(R.id.ivProfileImageDetail);
        TextView tvUserName = view.findViewById(R.id.tvUserName);
        ImageButton ibOptions = view.findViewById(R.id.ibOptions);
        ImageView ivImage = view.findViewById(R.id.ivImage);
        ImageButton ibLike = view.findViewById(R.id.ibLike);
        ImageButton ibComment = view.findViewById(R.id.ibComment);
        ImageButton ibMessage = view.findViewById(R.id.ibMessage);
        ImageButton ibBookmark = view.findViewById(R.id.ibBookmark);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvTimeStamp = view.findViewById(R.id.tvTimeStamp);

        post = getArguments().getParcelable("post");
        Log.d("PostDetailsActivity", "Showing details for post.");

        tvUserName.setText(post.getUser().getUsername());
        tvDescription.setText(String.format("%s %s", post.getUser().getUsername(), post.getDescription()));
        tvTimeStamp.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivImage);
        Glide.with(view.getContext())
                .load(post.getUser().getParseFile("profileImage").getUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfileImage);
    }

    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    private String getRelativeTimeAgo(String rawJsonDate) {
        String postFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(postFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
