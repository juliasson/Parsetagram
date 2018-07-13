package me.juliasson.parsetagram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
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
import java.util.List;
import java.util.Locale;

import me.juliasson.parsetagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.tvUserName.setText(post.getUser().getUsername());
        holder.tvDescription.setText(String.format("%s %s", post.getUser().getUsername(), post.getDescription()));
        holder.tvTimeStamp.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

        Glide.with(mContext)
                .load(post.getImage().getUrl())
                .into(holder.ivImage);

        Glide.with(mContext)
                .load(post.getUser().getParseFile("profileImage").getUrl())
                .apply(RequestOptions.overrideOf(70, 70))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ibOptions = itemView.findViewById(R.id.ibOptions);
            ivImage = itemView.findViewById(R.id.ivImage);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
            ibMessage = itemView.findViewById(R.id.ibMessage);
            ibBookmark = itemView.findViewById(R.id.ibBookmark);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Post post = mPosts.get(position);
                        Bundle args = new Bundle();
                        args.putParcelable("post", post);
                        PostProfileFragment fragment = new PostProfileFragment();
                        fragment.setArguments(args);
                        HomeActivity activity = (HomeActivity) mContext;
                        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.flContainer, fragment);
                        transaction.commit();
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = mPosts.get(position);
                Bundle args = new Bundle();
                args.putParcelable("post", post);
                PostDetailFragment fragment = new PostDetailFragment();
                fragment.setArguments(args);
                HomeActivity activity = (HomeActivity)mContext;
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, fragment);
                transaction.commit();
            }
        }
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

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}
