package cj1098.animeshare;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cj1098.animedetails.AnimeDetailsFragment;
import cj1098.animeshare.userList.SmallAnimeObject;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter<ShowsRecyclerAdapter.ShowsViewHolder> {
    private Context mContext;
    private List<SmallAnimeObject> mAnimeObjects;
    private static final int MAX_RATING = 100;

    public ShowsRecyclerAdapter(Context context, List<SmallAnimeObject> data) {
        this.mContext = context;
        this.mAnimeObjects = data;
    }

    public static class ShowsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.anime_title) TextView title;
        @Bind(R.id.anime_image) ImageView image;
        @Bind(R.id.anime_rating) RatingBar rb;
        @Bind(R.id.anime_episode_count) TextView episodeCount;
        @Bind(R.id.image_loader) ProgressBar pb;

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
        Glide.with(mContext)
                .load(mAnimeObjects.get(position).getImageUrlLge())
                .asBitmap()
                .error(R.drawable.code_geass)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        holder.pb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.pb.setVisibility(View.GONE);
                        holder.image.setAdjustViewBounds(true);
                        holder.image.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        holder.pb.setVisibility(View.GONE);
                        holder.image.setImageDrawable(errorDrawable);
                    }
                });
        holder.title.setText(mAnimeObjects.get(position).getTitleEnglish());
        if (mAnimeObjects.get(position).getAverageScore() != null) {
            holder.rb.setMax(MAX_RATING);
            holder.rb.setRating(mAnimeObjects.get(position).getAverageScore().floatValue() / 200f * 10f);
        }

        holder.episodeCount.setText(mContext.getResources().getString(R.string.anime_total_episodes_string,
                Integer.toString(mAnimeObjects.get(position).getTotalEpisodes())));


        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof HomeActivity) {
                FragmentManager fragmentManager= ((HomeActivity)mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.base_content, AnimeDetailsFragment.newInstance(), AnimeDetailsFragment.TAG).commit();

            }
        });
    }

    public void addBatchResponseToList(List<SmallAnimeObject> newAnimeObjects) {
        if (newAnimeObjects.size() > 0) {
            mAnimeObjects.addAll(newAnimeObjects);
            notifyItemRangeInserted(mAnimeObjects.size() - newAnimeObjects.size(), mAnimeObjects.size());
        }
    }

    public void refreshListWithNewData(List<SmallAnimeObject> newAnimeObjects) {
        mAnimeObjects.clear();
        mAnimeObjects = new ArrayList<>();
        mAnimeObjects.addAll(newAnimeObjects);
        notifyItemRangeInserted(0, mAnimeObjects.size());
    }
    @Override
    public int getItemCount() {
        return mAnimeObjects.size();
    }

}