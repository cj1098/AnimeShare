package cj1098.animeshare;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import cj1098.animeshare.animelist.AnimeListFragment;
import cj1098.animeshare.home.HomeHeadlessFragment;
import cj1098.animeshare.service.UserInformationService;
import cj1098.animeshare.viewmodels.UserViewModel;
import cj1098.base.BaseActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        super.onCreateOptionsMenu(menu);
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
            ObjectMapper objectMapper = new ObjectMapper();
            JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(objectMapper);

            OkHttpClient.Builder client = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d(TAG, message));
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            client.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://50.132.216.152")
                    .client(client.build())
                    .addConverterFactory(jacksonConverterFactory)
                    .build();

            UserInformationService userService = retrofit.create(UserInformationService.class);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", "test2");
                jsonObject.put("device_manufacturer", "duhh");
                jsonObject.put("android_version", "6.5.2");
                jsonObject.put("phone_model", "nexus6");
                jsonObject.put("locale", "US");
                jsonObject.put("password", "password1");
            }
            catch (JSONException je) {

            }
            UserViewModel viewmodel = new UserViewModel("test2", "what the actual fuck", "6.5.2", "nexus6", "US", "password6");
            Call<UserViewModel> call = userService.sendUserInfo(viewmodel);
            call.enqueue(new Callback<UserViewModel>() {
                @Override
                public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {
                    Toast.makeText(HomeActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<UserViewModel> call, Throwable t) {
                    Log.d(TAG, t.toString());
                }
            });
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
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mNavView = (NavigationView) findViewById(R.id.main_nav);

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
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu();
                }
            };
            mNavView.setNavigationItemSelectedListener(this);
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            mToolbar.setNavigationOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
            mDrawerToggle.syncState();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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

    public class MyAsync extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                URL url = new URL("http://50.132.216.152/sendInformation.php");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.flush();
                os.close();
                InputStream in = conn.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                String test = "";
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    test += current;
                }
                String blah = test;
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this, blah, Toast.LENGTH_SHORT).show();
                    }
                });
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                conn.connect();
            }
            catch (MalformedURLException u) {

            }
            catch (IOException io ) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(HomeActivity.this, "asdas", Toast.LENGTH_LONG).show();
        }
    }
}