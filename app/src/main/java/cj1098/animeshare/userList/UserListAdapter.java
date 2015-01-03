package cj1098.animeshare.userList;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

import cj1098.animeshare.MyApplication;
import cj1098.animeshare.R;

public class UserListAdapter extends BaseAdapter {
    ArrayList<ListItem> data;
    LayoutInflater mInflater;
    Context context;
    MyApplication myApp;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    public UserListAdapter(Context context, ArrayList<ListItem> data) {
        this.context = context;
        this.data = data;
        this.myApp = myApp;
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }


    private static class ViewHolder {
        TextView title;
        TextView synopsis;
        WebImageView image;
        RatingBar rb;

        public TextView getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(TextView synopsis) {
            this.synopsis = synopsis;
        }

        public RatingBar getRb() {
            return rb;
        }

        public void setRb(RatingBar rb) {
            this.rb = rb;
        }

        private ViewHolder(TextView title, TextView synopsis, WebImageView image, RatingBar rb) {
            this.title = title;
            this.synopsis = synopsis;
            this.image = image;
            this.rb = rb;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public WebImageView getImage() {
            return image;
        }

        public void setImage(WebImageView image) {
            this.image = image;
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ListItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;
        TextView synopsis;
        WebImageView image;
        RatingBar rb;
        if (convertView == null) {
            mInflater = ((Activity)context).getLayoutInflater();
            convertView = mInflater.inflate(R.layout.userlist_grid_singleview, parent, false);
            title = (TextView)convertView.findViewById(R.id.anime_title);
            synopsis = (TextView)convertView.findViewById(R.id.anime_description);
            image = (WebImageView)convertView.findViewById(R.id.anime_image);
            rb = (RatingBar)convertView.findViewById(R.id.anime_rating);
            //synopsis.setText(data.get(position).getSynopsis());
            convertView.setTag(new ViewHolder(title, synopsis, image, rb));
        }
        else {
            ViewHolder vh = (ViewHolder)convertView.getTag();
            title = vh.getTitle();
            synopsis = vh.getSynopsis();
            image = vh.getImage();
            rb = vh.getRb();
        }

        image.setBackground(new BitmapDrawable(context.getResources(), imageLoader.loadImageSync(getItem(position).getCover_image(), options)));
        //ImageLoader.getInstance().displayImage(getItem(position).getCover_image(), image, options);
        //image.setPlaceholderImage(R.drawable.ic_launcher);
        //image.setImageUrl(getItem(position).getCover_image());
        title.setText(getItem(position).getTitle());
        rb.setRating(getItem(position).getCommunity_rating());


        //synopsis.setText(data.get(position).getSynopsis());
        return convertView;
    }


}

