package cj1098.animeshare;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ShowsFragment.OnFragmentInteractionListener {
    public static HashMap<String, Drawable> sCachedImages;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerRecyclerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.base_content) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.base_content, ShowsFragment.newInstance());
            ft.commit();
        }

        setUpToolbar();
        setUpNavDrawer();

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

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void setUpRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DrawerRecyclerAdapter(this, new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.drawer_items))));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            mDrawerToggle = new ActionBarDrawerToggle(
                    this,                  /* host Activity */
                    mDrawerLayout,         /* DrawerLayout object */
                    mToolbar,  /* nav drawer icon to replace 'Up' caret */
                    R.string.drawer_open,  /* "open drawer" description */
                    R.string.drawer_close  /* "close drawer" description */
            ) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mNavView = (NavigationView) findViewById(R.id.main_nav);
            mNavView.setNavigationItemSelectedListener(this);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // TODO: replace with switch
        if (menuItem.getItemId() == R.id.navigation_item_1) {
            Snackbar.make(mToolbar, "1", Snackbar.LENGTH_SHORT).show();
            if (getSupportFragmentManager().findFragmentById(R.id.base_content) != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.base_content, ShowsFragment.newInstance());
                ft.commit();
            }
        } else if (menuItem.getItemId() == R.id.navigation_item_2) {
            Snackbar.make(mToolbar, "2", Snackbar.LENGTH_SHORT).show();
            if (getSupportFragmentManager().findFragmentById(R.id.base_content) != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.base_content, RadioStreamFragment.newInstance());
                ft.commit();
            }
        } else if (menuItem.getItemId() == R.id.navigation_item_3) {

        } else if (menuItem.getItemId() == R.id.navigation_item_4) {

        } else if (menuItem.getItemId() == R.id.navigation_item_5) {

        } else {

        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();

        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}