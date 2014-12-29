package cj1098.animeshare.userList;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import cj1098.animeshare.R;

public class UserListAdapter extends BaseAdapter {
    ArrayList<ListItem> data;
    LayoutInflater mInflater;
    Context context;

    public UserListAdapter(Context context, ArrayList<ListItem> data) {
        this.context = context;
        this.data = data;
    }


    private static class ViewHolder {
        TextView title;
        TextView synopsis;
        ImageView image;
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

        private ViewHolder(TextView title, TextView synopsis, ImageView image, RatingBar rb) {
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

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
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
        ImageView image;
        RatingBar rb;
        if (convertView == null) {
            mInflater = ((Activity)context).getLayoutInflater();
            convertView = mInflater.inflate(R.layout.userlist_grid_singleview, parent, false);
            title = (TextView)convertView.findViewById(R.id.anime_title);
            synopsis = (TextView)convertView.findViewById(R.id.anime_description);
            image = (ImageView)convertView.findViewById(R.id.anime_image);
            rb = (RatingBar)convertView.findViewById(R.id.anime_rating);
            rb.setRating(getItem(position).getCommunity_rating());
            title.setText(getItem(position).getTitle());
            new DownloadImageTask((ImageView)convertView.findViewById(R.id.anime_image))
                    .execute(getItem(position).getCover_image());
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

        title.setText(getItem(position).getTitle());
        rb.setRating(getItem(position).getCommunity_rating());
        new DownloadImageTask((ImageView)convertView.findViewById(R.id.anime_image))
                .execute(getItem(position).getCover_image());
        //synopsis.setText(data.get(position).getSynopsis());
        return convertView;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Drawable d = new BitmapDrawable(context.getResources(), result);
            bmImage.setBackground(d);
            notifyDataSetChanged();
        }
    }

}