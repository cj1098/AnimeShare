package cj1098.animeshare;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cj1098.animeshare.userList.ListItem;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter<ShowsRecyclerAdapter.ShowsViewHolder> {
    private Context mContext;
    private ArrayList<ListItem> mShowObjects;

    public ShowsRecyclerAdapter(Context context, ArrayList<ListItem> data) {
        this.mContext = context;
        this.mShowObjects = data;
        Collections.sort(mShowObjects);
    }

    public static class ShowsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.anime_title) TextView title;
        @BindView(R.id.anime_description) TextView synopsis;
        @BindView(R.id.anime_image) ImageView image;
        @BindView(R.id.anime_rating) RatingBar rb;
        @BindView(R.id.image_loader) ProgressBar pb;

        public ShowsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.anime_image:
                   break;
            }
        }
    }

    @Override
    public ShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.userlist_grid_singleview, parent, false);
        return new ShowsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ShowsViewHolder holder, final int position) {
        holder.pb.setVisibility(View.VISIBLE);
        SimpleTarget showTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                holder.pb.setVisibility(View.GONE);
                holder.image.setImageBitmap(bitmap);
            }
        };

        // not sure why Android studio is complaining here.. figure it out later. TODO
        Glide.with(mContext)
                .load(mShowObjects.get(holder.getAdapterPosition()).getCover_image())
                .asBitmap()
                .error(R.drawable.code_geass)
                .into(showTarget);
        holder.image.setAdjustViewBounds(true);
        holder.title.setText(mShowObjects.get(holder.getAdapterPosition()).getTitle());
        holder.rb.setRating(mShowObjects.get(holder.getAdapterPosition()).getCommunity_rating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mShowObjects.get(position).getTitle())
                        .setMessage(mShowObjects.get(position).getSynopsis())
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShowObjects.size();
    }

}