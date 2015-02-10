package cj1098.animeshare.userList;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import cj1098.animeshare.R;

/**
 * Need to do an initial load I think... either that or I need to make
 * a progress bar for when the user first goes into the list. Otherwise
 * it's just a blank white screen.
 */

public class UserList extends Activity {
    private UserListAdapter adapter;
    private ProgressBar animatedLoader;
    private GridView mGridView;
    private ArrayList<ListItem> userList = new ArrayList<ListItem>();
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading = false;
    private int startingId = 1;
    private int endingId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_grid);
        task task = new task();
        task.execute();
        initControls();
    }

    /**
     * setup the gridView and tell it to listen for scroll changes and act accordingly.
     */
    private void initControls() {
        mGridView = (GridView)findViewById(R.id.user_gridlist);
        animatedLoader = (ProgressBar)findViewById(R.id.gridview_loader);

        adapter = new UserListAdapter(this, userList);
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                currentScrollState = scrollState;
                if (mGridView.getLastVisiblePosition() == adapter.getCount() - 1 &&
                        mGridView.getChildAt(mGridView.getChildCount() - 1).getBottom() <= mGridView.getHeight()) {
                    isScrollCompleted();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                currentVisibleItemCount = visibleItemCount;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserList.this);
                builder.setTitle(userList.get(position).getTitle())
                        .setMessage(userList.get(position).getSynopsis())
                        .show();
            }
        });
    }


    public class task extends AsyncTask<Void, Integer, Void> {
        //ProgressDialog pd;

        /**
         * setup the progressbar
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pd = new ProgressDialog(UserList.this);
            pd.setMessage("Bare with me. We're loading a bunch'a shit right now..");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setIndeterminate(false);
            pd.setMax(100);
            pd.show();*/
        }

        //this just takes an integer to update by. Just set it by whatever you need.
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //pd.incrementProgressBy(values[0]);
        }

        /**
         * httpGet to hummingBird-v1 api to get JSON objects back and parse them using jackson. (Really cool) parcelable.com
         * Also publishes the progress of the process to the user via a progressBar
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(Void... params) {
            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                ObjectMapper mapper = new ObjectMapper();
                for (;startingId <= endingId; startingId++) {

                    URI website = new URI("https://hummingbirdv1.p.mashape.com/anime/" + startingId);
                    request.setHeader("X-Mashape-Key", "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy");
                    request.setURI(website);
                    HttpResponse response = httpclient.execute(request);
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));

                    String line = in.readLine();

                    mapper.setVisibilityChecker(mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                    userList.add(mapper.readValue(line, ListItem.class));
                    publishProgress(1);
                }

            }catch(Exception e){
                Log.e("log_tag", "Error in http connection " + e.toString());
            }
            return null;
        }

        /**
         * This is for checking if the user has initially loaded the list or if they're in the process of loading more data for the list.
         * if they're loading the list for the first time, then set the adapter. Otherwise, notify the adapter that the data set has changed
         * and tell it to re-instantiate its views.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //pd.cancel();

            if (isLoading) {
                adapter.notifyDataSetChanged();
                animatedLoader.setVisibility(View.GONE);
            }
            else {
                mGridView.setAdapter(adapter);

            }
        }
    }


    /**
     * I'll try my best to explain this. This checks if the user has finished their scroll "action"
     * it makes sure the scroll state is idle and check if you're currently loading data.
     */
    private void isScrollCompleted() {
        if (currentVisibleItemCount > 0 && currentScrollState == GridView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                endingId += 10;
                task increment = new task();
                increment.execute();
                animatedLoader.setVisibility(View.VISIBLE);
            }
            else if (isLoading) {
                isLoading = false;
            }
        }
    }





}