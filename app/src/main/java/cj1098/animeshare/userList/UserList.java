package cj1098.animeshare.userList;


import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import cj1098.animeshare.R;

public class UserList extends Activity {
    private ArrayList<ListItem> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_grid);
        Bundle b = getIntent().getExtras();
        userList = b.getParcelableArrayList("list");
        initControls(userList);
    }

    private void initControls(ArrayList<ListItem> data) {
        GridView mGridView = (GridView)findViewById(R.id.user_gridlist);
        UserListAdapter adapter = new UserListAdapter(this, data);
        mGridView.setAdapter(adapter);
    }

}