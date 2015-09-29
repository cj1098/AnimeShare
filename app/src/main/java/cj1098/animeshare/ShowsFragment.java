package cj1098.animeshare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cj1098.animeshare.userList.ListItem;
import cj1098.animeshare.userList.UserListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowsFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private UserListAdapter adapter;
    private ProgressBar animatedLoader;
    private GridView mGridView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ListItem> userList = new ArrayList<ListItem>();
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading = false;
    private int startingId = 1;
    private int endingId = 10;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowsFragment newInstance(String param1, String param2) {
        ShowsFragment fragment = new ShowsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shows, container, false);

        mGridView = (GridView)v.findViewById(R.id.user_gridlist);
        animatedLoader = (ProgressBar)v.findViewById(R.id.gridview_loader);
        initControls();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        task task = new task();
        task.execute();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * setup the gridView and tell it to listen for scroll changes and act accordingly.
     */
    private void initControls() {

        adapter = new UserListAdapter(getActivity(), userList);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(userList.get(position).getTitle())
                        .setMessage(userList.get(position).getSynopsis())
                        .show();
            }
        });
    }

    private void setUpRecyclerView(View view) {
        //mRecyclerView = (RecyclerView)


    }

    private void setUpToolbar() {

    }

    public class task extends AsyncTask<Void, Integer, Void> {
        //ProgressDialog pd;
        JSONObject json;

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
                ObjectMapper mapper = new ObjectMapper();
                for (;startingId <= endingId; startingId++) {
                    URL url = new URL("https://hummingbirdv1.p.mashape.com/anime/" + startingId);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("X-Mashape-Key", "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy");
                    conn.setReadTimeout(15 * 1000);
                    InputStream is = conn.getInputStream();
                    InputStreamReader isw = new InputStreamReader(is);
                    BufferedReader bf = new BufferedReader(isw);
                    StringBuilder stringBuilder = new StringBuilder();

                    String line = bf.readLine();

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
