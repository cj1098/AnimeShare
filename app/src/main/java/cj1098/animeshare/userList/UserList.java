package cj1098.animeshare.userList;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;

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

public class UserList extends Activity {
    private UserListAdapter adapter;
    private GridView mGridView;
    private ArrayList<ListItem> userList = new ArrayList<>();
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

    private void initControls() {
        mGridView = (GridView)findViewById(R.id.user_gridlist);
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
    }


    public class task extends AsyncTask<Void, Integer, Void> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UserList.this);
            pd.setMessage("Bare with me. We're loading a bunch'a shit right now..");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setIndeterminate(false);
            pd.setMax(100);
            pd.show();
        }

        //this just takes an integer to update by. Just set it by whatever you need.
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pd.incrementProgressBy(values[0]);
        }

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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.cancel();

            if (isLoading) {
                adapter.notifyDataSetChanged();
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
            }
            else if (isLoading) {
                isLoading = false;
            }
        }
    }





}