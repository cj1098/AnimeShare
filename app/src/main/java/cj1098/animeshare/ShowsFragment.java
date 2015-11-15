package cj1098.animeshare;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import cj1098.animeshare.CustomViews.SpacesItemDecoration;
import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.userList.ListItem;


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
    private static final String TAG = ShowsFragment.class.getName();

    private ProgressBar animatedLoader;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ListItem> userList = new ArrayList<ListItem>();
    private boolean isLoading = false;
    private int startingId = 1;
    private int endingId = 10;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShowsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowsFragment newInstance() {
        ShowsFragment fragment = new ShowsFragment();
        Bundle args = new Bundle();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shows, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.user_gridlist);
        animatedLoader = (ProgressBar)v.findViewById(R.id.gridview_loader);
        initControls();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        AnimeRequestService service = new AnimeRequestService(getActivity(), isLoading, mRecyclerView, userList);
        service.callService(endingId - 9, endingId);
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
        void onFragmentInteraction(Uri uri);
    }

    /**
     * setup the recyclerview and tell it to listen for scroll changes and act accordingly.
     */
    private void initControls() {

        mAdapter = new ShowsRecyclerAdapter(getActivity(), userList);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(50));
        mRecyclerView.setLayoutManager(mLayoutManager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                isScrollCompleted();
            }
        });
    }

    private void setUpRecyclerView(View view) {
        //mRecyclerView = (RecyclerView)

    }

    private void setUpToolbar() {

    }

    // TODO: remove if not using
    public class Task extends AsyncTask<Void, Integer, Void> {
        //ProgressDialog pd;
        JSONObject json;

        /**
         * setup the progressbar
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            animatedLoader.setVisibility(View.VISIBLE);
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
         *
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                for (; startingId <= endingId; startingId++) {
                    URL url = new URL("https://hummingbirdv1.p.mashape.com/anime/" + startingId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("X-Mashape-Key", "rasJF18hhHmshDKpDzwpvlmZt5rAp1YrLFdjsn2XGCcBALFoQy");
                    InputStream is = conn.getInputStream();
                    InputStreamReader isw = new InputStreamReader(is);
                    BufferedReader bf = new BufferedReader(isw);

                    String line = bf.readLine();

                    mapper.setVisibilityChecker(mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                    userList.add(mapper.readValue(line, ListItem.class));
                    publishProgress(1);
                }

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
            }
            return null;
        }

        /**
         * This is for checking if the user has initially loaded the list or if they're in the process of loading more data for the list.
         * if they're loading the list for the first time, then set the adapter. Otherwise, notify the adapter that the data set has changed
         * and tell it to re-instantiate its views.
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isLoading) {
                mAdapter.notifyDataSetChanged();
                animatedLoader.setVisibility(View.GONE);
            } else {
                mRecyclerView.setAdapter(mAdapter);

            }
        }
    }

    /**
     * I'll try my best to explain this. This checks if the user has finished their scroll "action"
     * it makes sure the scroll state is idle and check if you're currently loading data.
     */
    private void isScrollCompleted() {
        if ((mLayoutManager.findFirstVisibleItemPosition() + mLayoutManager.getChildCount()) >= mLayoutManager.getItemCount() - 6) {
            if (!isLoading) {

                endingId += 10;
                isLoading = true;
                AnimeRequestService service = new AnimeRequestService(getActivity(), isLoading, mRecyclerView, userList);
                service.callService(endingId - 9, endingId);

                /*Task increment = new Task();
                increment.execute();*/
            } else {
                isLoading = false;
            }
        }
    }
}