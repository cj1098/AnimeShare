package cj1098.animeshare.animelist;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cj1098.animeshare.CustomViews.DotLoader;
import cj1098.animeshare.CustomViews.SpacesItemDecoration;
import cj1098.animeshare.R;
import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.util.DaggerUtil;
import cj1098.animeshare.util.NetworkUtil;
import cj1098.base.BaseFragment;
import cj1098.event.NoNetworkEvent;
import cj1098.event.RxBus;
import cj1098.event.SlowNetworkEvent;


/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link AnimeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimeListFragment extends BaseFragment implements AnimeListMvp.View {
    public static final String TAG = AnimeListFragment.class.getName();

    @Inject
    AnimeListMvp.Presenter mAnimeListPresenter;

    private ProgressBar mAnimatedLoader;
    private DotLoader mDotLoader;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ShowsRecyclerAdapter mAdapter;
    private boolean isLoading = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnimeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnimeListFragment newInstance() {
        AnimeListFragment fragment = new AnimeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shows, container, false);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.user_gridlist);
        mAnimatedLoader = (ProgressBar)v.findViewById(R.id.gridview_loader);
        initControls();
        if (NetworkUtil.isConnected(getContext())) {
            mAnimeListPresenter.makeAuthRequest();
        }
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        mAnimeListPresenter.attachView(this);
        mAnimeListPresenter.subscribeToObservables();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAnimeListPresenter.unsubscribeFromObservables();
        mAnimeListPresenter.detachView();
    }

    /**
     * setup the recyclerview and tell it to listen for scroll changes and act accordingly.
     */
    private void initControls() {
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
     * TODO: check for flings as well and do the same thing.
     */
    private void isScrollCompleted() {
        if ((mLayoutManager.findFirstVisibleItemPosition() + mLayoutManager.getChildCount()) >= mLayoutManager.getItemCount() - 3) {
            if (!isLoading) {

                isLoading = true;
                //TODO: add a connected slow check and then post a slowNetwork event that would display either no network speed
                //TODO: or a too slow to operate speed.
                if (NetworkUtil.isConnected(getContext())) {
                    // TODO: presenter should make the call here and everywhere in this class.
                    mAnimeListPresenter.makeBatchCall(Integer.toString(mLayoutManager.getItemCount() / 40) + 1);
                    //mRequestService.getAnimeBatch("1ndqqBfOCq9FpXTKsPB5NE4JR8Fp5iQy6kctMfnK", Integer.toString(mLayoutManager.getItemCount() / 40) + 1);
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
    // region AnimeListMvp interface methods

    @Override
    public void updateAnimeList(List<AnimeObject> animeList) {
        if (mLayoutManager.getItemCount() == 0) {
            mAdapter = new ShowsRecyclerAdapter(getActivity(), animeList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.addBatchResponseToList(animeList);
        }
    }

    // endregion

}