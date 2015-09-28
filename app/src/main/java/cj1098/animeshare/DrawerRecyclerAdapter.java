package cj1098.animeshare;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.DrawerViewHolder> {

    private Context context;
    private ArrayList<String> data;

    public DrawerRecyclerAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    public static class DrawerViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
        }
    }

    @Override
    public DrawerRecyclerAdapter.DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false);
        DrawerViewHolder viewHolder = new DrawerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerRecyclerAdapter.DrawerViewHolder viewHolder, int position) {
        viewHolder.name.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}