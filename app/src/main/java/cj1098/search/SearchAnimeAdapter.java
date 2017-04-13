package cj1098.search;

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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cj1098.animeshare.R;
import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.userList.SearchAnimeObject;
import cj1098.animeshare.userList.SmallAnimeObject;

/**
 * Created by chris on 12/31/16.
 */

public class SearchAnimeAdapter extends RecyclerView.Adapter<SearchAnimeAdapter.SearchAnimeViewHolder> {
    private Context mContext;
    private List<SearchAnimeObject> mAnimeObjects = new ArrayList<>();
    private static final int MAX_RATING = 100;

    public SearchAnimeAdapter(Context context, List<SearchAnimeObject> data) {
        this.mContext = context;
        this.mAnimeObjects = data;
    }

    public static class SearchAnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.anime_title)
        TextView title;
        @Bind(R.id.anime_image)
        ImageView image;
        @Bind(R.id.anime_rating)
        RatingBar rb;
        @Bind(R.id.anime_episode_count)
        TextView episodeCount;
        @Bind(R.id.image_loader)
        ProgressBar pb;

        public SearchAnimeViewHolder(View itemView) {
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
    public SearchAnimeAdapter.SearchAnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.userlist_grid_singleview, parent, false);
        return new SearchAnimeAdapter.SearchAnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SearchAnimeAdapter.SearchAnimeViewHolder holder, final int position) {
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
        holder.rb.setMax(MAX_RATING);
        holder.rb.setRating(mAnimeObjects.get(position).getAverageScore() / 200f * 10f);

        holder.episodeCount.setText(mContext.getResources().getString(R.string.anime_total_episodes_string,
                Integer.toString(mAnimeObjects.get(position).getTotalEpisodes())));


        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(mAnimeObjects.get(position).getTitleEnglish())
                    .setMessage(Float.toString(mAnimeObjects.get(position).getAverageScore()))
                    .show();
        });
    }

    public void refreshListWithNewData(List<SearchAnimeObject> newAnimeObjects) {
        mAnimeObjects.clear();
        mAnimeObjects = new ArrayList<>();
        mAnimeObjects.addAll(newAnimeObjects);
        // NOTE: notifyItemRangeChanged here has odd behavior if the list updates to 1 item.
        notifyDataSetChanged();
    }


    public void clearSearchData() {
        int previousListSize = mAnimeObjects.size();
        mAnimeObjects.clear();
        mAnimeObjects = new ArrayList<>();
        notifyItemRangeRemoved(0, previousListSize);
    }
    @Override
    public int getItemCount() {
        return mAnimeObjects.size();
    }
}


