package com.example.frontend.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frontend.R;
import com.example.frontend.data.model.Post;
import com.example.frontend.ui.feed.PostImageAdapter;

import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public ProfilePostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvContent;
        ImageView imgAvatar;
        RecyclerView rvPostImages;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvAuthorName);
            tvContent = itemView.findViewById(R.id.tvContent);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            rvPostImages = itemView.findViewById(R.id.rvPostImages);
            if (rvPostImages != null) {
                rvPostImages.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
                rvPostImages.setOnFlingListener(null);
                PagerSnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(rvPostImages);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_posts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvContent.setText(post.getContent() != null ? post.getContent() : "");

        if (post.getAuthorId() != null) {
            holder.tvUserName.setText(post.getAuthorId().getUsername() != null ? post.getAuthorId().getUsername() : "Người dùng");
            Glide.with(context)
                    .load(post.getAuthorId().getAvatar())
                    .placeholder(R.drawable.ic_user)
                    .into(holder.imgAvatar);
        } else {
            holder.tvUserName.setText("Người dùng");
            holder.imgAvatar.setImageResource(R.drawable.ic_user);
        }

        if (holder.rvPostImages != null) {
            if (post.getImages() != null && !post.getImages().isEmpty()) {
                holder.rvPostImages.setVisibility(View.VISIBLE);
                holder.rvPostImages.setAdapter(new PostImageAdapter(context, post.getImages()));
            } else {
                holder.rvPostImages.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updateData(List<Post> newPosts) {
        this.posts = newPosts;
        notifyDataSetChanged();
    }
}
