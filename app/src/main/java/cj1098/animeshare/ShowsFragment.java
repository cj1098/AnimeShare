package cj1098.animeshare;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cj1098.animeshare.CustomViews.SpacesItemDecoration;
import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.util.NetworkUtil;
import cj1098.base.BaseFragment;
import cj1098.event.AnimeObjectReceivedEvent;
import cj1098.event.NoNetworkEvent;
import cj1098.event.RxBus;
import cj1098.event.SlowNetworkEvent;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link ShowsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowsFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = ShowsFragment.class.getName();

    private ProgressBar animatedLoader;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<AnimeObject> animeList = new ArrayList<AnimeObject>();
    private boolean isLoading = false;
    private int endingId = 10;
    private NetworkUtil networkUtil;
    private CompositeSubscription compositeSubscription;

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
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        if (getArguments() != null) {
        }
        compositeSubscription.add(RxBus.getInstance().register(AnimeObjectReceivedEvent.class, this::updateMainAnimeList));
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
        if (NetworkUtil.isConnected(getContext())) {
            AnimeRequestService service = new AnimeRequestService();
            service.callService(endingId - 9, endingId);
        }
    }

    /**
     * setup the recyclerview and tell it to listen for scroll changes and act accordingly.
     */
    private void initControls() {

        mAdapter = new ShowsRecyclerAdapter(getActivity(), animeList);
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
                //TODO: add a connected slow check and then post a slowNetwork event that would display either no network speed
                //TODO: or a too slow to operate speed.
                if (NetworkUtil.isConnected(getContext())) {
                    AnimeRequestService service = new AnimeRequestService();
                    service.callService(endingId - 9, endingId);
                }
                else if (!NetworkUtil.isConnectedFast(getContext())){
                    SlowNetworkEvent slowNetworkEvent = new SlowNetworkEvent();
                    RxBus.getInstance().post(slowNetworkEvent);
                }
                else {
                    NoNetworkEvent noNetworkEvent = new NoNetworkEvent();
                    RxBus.getInstance().post(noNetworkEvent);
                }

            } else {
                isLoading = false;
            }
        }
    }

    private void updateMainAnimeList(AnimeObjectReceivedEvent event) {
        if (!isDuplicateEntry(event.getAnimeObject())) {
            animeList.add(event.getAnimeObject());
        }
        if (mLayoutManager.getChildCount() == 0) {
            mAdapter = new ShowsRecyclerAdapter(getActivity(), animeList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyItemInserted(animeList.indexOf(event.getAnimeObject()));
        }
    }

    private boolean isDuplicateEntry(AnimeObject animeObject) {
        for (int i = 0; i < animeList.size(); i++) {
            if (animeObject.getId() == animeList.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

}