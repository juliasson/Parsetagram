package me.juliasson.parsetagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import me.juliasson.parsetagram.model.Post;

public class PostDetailActivity extends AppCompatActivity {

    Post post;
    Context context;

    public ImageView ivProfileImage;
    public TextView tvUserName;
    public ImageButton ibOptions;
    public ImageView ivImage;
    public ImageButton ibLike;
    public ImageButton ibComment;
    public ImageButton ibMessage;
    public ImageButton ibBookmark;
    public TextView tvDescription;
    public TextView tvTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        context = this;

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUserName = findViewById(R.id.tvUserName);
        ibOptions = findViewById(R.id.ibOptions);
        ivImage = findViewById(R.id.ivImage);
        ibLike = findViewById(R.id.ibLike);
        ibComment = findViewById(R.id.ibComment);
        ibMessage = findViewById(R.id.ibMessage);
        ibBookmark = findViewById(R.id.ibBookmark);
        tvDescription = findViewById(R.id.tvDescription);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);

        post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        Log.d("PostDetailsActivity", "Showing details for post.");

        tvUserName.setText(post.getUser().getUsername());
        tvDescription.setText(String.format("%s %s", post.getUser().getUsername(), post.getDescription()));
        tvTimeStamp.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(ivImage);
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
