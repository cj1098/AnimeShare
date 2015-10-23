package cj1098.animeshare;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import cj1098.animeshare.userList.ListItem;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter<ShowsRecyclerAdapter.ShowsViewHolder> {
    Context context;
    ArrayList<ListItem> data;
    DisplayImageOptions options;

    public ShowsRecyclerAdapter(Context context, ArrayList<ListItem> data) {
        this.context = context;
        this.data = data;
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.NONE_SAFE)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
    }


    public static class ShowsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView synopsis;
        ImageView image;
        RatingBar rb;
        ProgressBar pb;

        public ShowsViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.anime_title);
            synopsis = (TextView)itemView.findViewById(R.id.anime_description);
            image = (ImageView)itemView.findViewById(R.id.anime_image);
            rb = (RatingBar)itemView.findViewById(R.id.anime_rating);
            pb = (ProgressBar)itemView.findViewById(R.id.image_loader);
        }
    }

    @Override
    public ShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userlist_grid_singleview, parent, false);
        ShowsViewHolder viewHolder = new ShowsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShowsViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(data.get(position).getCover_image(), holder.image, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                holder.pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                holder.pb.setVisibility(View.GONE);
            }
        });
        holder.image.setPadding(0,0,0,0);
        holder.image.setAdjustViewBounds(true);
        //Picasso.with(context).setIndicatorsEnabled(true);
        //Picasso.with(context).load(data.get(position).getCover_image()).resize(100, 100).placeholder(R.drawable.logo).error(R.drawable.code_geass).into(holder.image);

        //image.setPlaceholderImage(R.drawable.ic_launcher);
        //image.setImageUrl(getItem(position).getCover_image());
        holder.title.setText(data.get(position).getTitle());
        holder.rb.setRating(data.get(position).getCommunity_rating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(data.get(position).getTitle())
                        .setMessage(data.get(position).getSynopsis())
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}