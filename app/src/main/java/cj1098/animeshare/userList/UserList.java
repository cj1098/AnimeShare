package cj1098.animeshare.userList;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;

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
    private ArrayList<ListItem> userList = new ArrayList<>();
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_grid);
        task task = new task();
        task.execute();
    }

    private void initControls() {
        ProgressBar pb = (ProgressBar)findViewById(R.id.api_load_progress);
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
                for (int i = 1; i < 100; i++) {
                    HttpClient httpclient = new DefaultHttpClient();

                    HttpGet request = new HttpGet();
                    URI website = new URI("https://hummingbirdv1.p.mashape.com/anime/" + i);
                    request.setHeader("X-Mashape-Key", "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy");
                    request.setURI(website);
                    HttpResponse response = httpclient.execute(request);
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            response.getEntity().getContent()));

                    String line = in.readLine();

                    ObjectMapper mapper = new ObjectMapper();
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
            GridView mGridView = (GridView)findViewById(R.id.user_gridlist);
            UserListAdapter adapter = new UserListAdapter(UserList.this, userList);
            mGridView.setAdapter(adapter);
        }
    }


}