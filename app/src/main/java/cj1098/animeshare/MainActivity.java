package cj1098.animeshare;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;

import cj1098.animeshare.userList.UserList;


public class MainActivity extends Activity {
    public static HashMap<String, Drawable> cachedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getActionBar();
        ab.setLogo(R.drawable.logo);

        String[] elements = new String[10];
        for (int i = 0; i< 10; i++) {
            elements[i] = String.valueOf(i);
        }

        HomeScreenAdapter adapter = new HomeScreenAdapter(this,elements);


        final ListView list = (ListView) findViewById(R.id.list3d);
        list.setDivider(null);
        list.setAdapter(adapter);
        /**list.setOverscrollHeader();
        list.setOverscrollFooter();*/
        list.setSelector(new ColorDrawable(0x0));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //launch the user into their "list" so they can add/edit it.
                        Intent userList = new Intent(MainActivity.this, UserList.class);
                        startActivity(userList);

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
