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
    private static final String TAG = ShowsFragment.class.getName();

    private ProgressBar animatedLoader;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<ListItem> userList = new ArrayList<ListItem>();
    private boolean isLoading = false;
    private int startingId = 1;
    private int endingId = 10;

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

        mRecyclerView = (RecyclerView) v.findViewById(R.id.user_gridlist);
        animatedLoader = (ProgressBar) v.findViewById(R.id.gridview_loader);
        initControls();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        AnimeRequestService service = new AnimeRequestService(getActivity(), isLoading, mRecyclerView, userList);
        service.callService(endingId - 9, endingId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

            } else {
                isLoading = false;
            }
        }
    }
}