package cj1098.animeshare;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import cj1098.animeshare.home.HomeHeadlessFragment;
import cj1098.base.BaseActivity;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static HashMap<String, Drawable> sCachedImages;
    private Toolbar mToolbar;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerRecyclerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private ActionBarDrawerToggle mDrawerToggle;

    private HomeHeadlessFragment homeHeadlessFragment;
    private AnimeListFragment animeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if our savedInstanceState is null that means the user is in the app without a previous instance.
        // add everything from scratch
        if (savedInstanceState == null) {
            addAnimeListFragment();
            addHomeHeadlessFragment();
        }
        // otherwise, re-initialize everything here.
        else {
            homeHeadlessFragment = (HomeHeadlessFragment)getSupportFragmentManager().findFragmentByTag(HomeHeadlessFragment.TAG);
            animeListFragment =  (AnimeListFragment)getSupportFragmentManager().findFragmentByTag(AnimeListFragment.TAG);
        }

        setUpToolbar();
        setUpNavDrawer();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mDrawerLayout != null) {
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(GravityCompat.START);
            menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null && savedInstanceState != null) {
            mDrawerToggle.syncState();
        }
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
                    mToolbar.setTitle("Options");
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    mToolbar.setTitle("AnimeShare");
                    invalidateOptionsMenu();
                }
            };
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mNavView = (NavigationView) findViewById(R.id.main_nav);
            mNavView.setNavigationItemSelectedListener(this);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
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
                ft.replace(R.id.base_content, AnimeListFragment.newInstance());
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

    private void addHomeHeadlessFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(HomeHeadlessFragment.newInstance(), HomeHeadlessFragment.TAG);
        ft.commit();
    }

    private void addAnimeListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.base_content, AnimeListFragment.newInstance(), AnimeListFragment.TAG);
        ft.commit();
    }

}