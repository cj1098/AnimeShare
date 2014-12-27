package cj1098.animeshare;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeScreenAdapter extends BaseAdapter{

    private final LayoutInflater mInflater;
    private final String[] mItems;

    public HomeScreenAdapter(Activity c,String[] objects) {
        mInflater = c.getLayoutInflater();
        mItems = objects;
    }

    public int getCount() {
        return mItems.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.homescreen_listitem, null);
            text = (TextView)convertView.findViewById(R.id.textView_number);
            text.setText("HEE");
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}