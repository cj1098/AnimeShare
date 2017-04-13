package cj1098.animeshare.animelist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.simpleframework.xml.Root;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import cj1098.animeshare.CustomViews.DotLoader;
import cj1098.animeshare.CustomViews.SpacesItemDecoration;
import cj1098.animeshare.CustomViews.WrapContentGridLayoutManager;
import cj1098.animeshare.R;
import cj1098.animeshare.ShowsRecyclerAdapter;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.userList.SmallAnimeObject;
import cj1098.animeshare.util.DaggerUtil;
import cj1098.animeshare.util.NetworkUtil;
import cj1098.animeshare.util.UiUtil;
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

    @Bind(R.id.anime_sort_fab)
    FloatingActionButton mSortingFAB;

    @Bind(R.id.anime_sort_fab_one)
    FloatingActionButton mGenreFAB;

    @Bind(R.id.anime_sort_fab_two)
    FloatingActionButton mSeasonsFAB;

    @Bind(R.id.anime_sort_fab_three)
    FloatingActionButton mPopularFAB;

    @Bind(R.id.fab_container_one)
    ViewGroup mFABContainerOne;

    @Bind(R.id.fab_container_two)
    ViewGroup mFABContainerTwo;

    @Bind(R.id.fab_container_three)
    ViewGroup mFABContainerThree;

    @Bind(R.id.fab_shadow_container)
    ViewGroup mFABShadowOverlay;

    @Bind(R.id.user_gridlist)
    RecyclerView mRecyclerView;

    @Bind(R.id.gridview_loader)
    ProgressBar mAnimatedLoader;

    private DotLoader mDotLoader;
    private GridLayoutManager mLayoutManager;
    private ShowsRecyclerAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isFABOpen = false;

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

        if (NetworkUtil.isConnected(getContext())) {
            mAnimeListPresenter.makeAuthRequest();
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initControls();
        setupFAB();
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

    private void setupFAB() {
        mSortingFAB.setOnClickListener(view -> {
            if (!isFABOpen) {
                showFABMenu();
            }
            else {
                hideFABMenu();
            }
            // TODO: create a transparent fragment and then animate however many FAB's above this one. Mimic CrunchyRoll

        });
    }

    private void showFABMenu() {
        isFABOpen = true;
        mSortingFAB.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_close_white, null));

        // px is the amount of margin space we give the view in xml and half is half of the normal FAB's size.
        // This is roughly, ROUGHLY in the middle of the FAB for the circular reveal.
        Resources resources = getActivity().getResources();
        float px = resources.getDimensionPixelSize(R.dimen.fab_margin_bottom_right);
        float half = resources.getDimensionPixelSize(R.dimen.normal_fab_size) / 2;

        int cx = (int)(mSortingFAB.getX() + px);
        int cy = (int)(mSortingFAB.getY() + half - px);

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(mFABShadowOverlay, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        mFABShadowOverlay.setVisibility(View.VISIBLE);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        anim.start();

        // animate the FAB'S
        mFABContainerOne.animate().translationY(-getResources().getDimension(R.dimen.first_fab_translation));
        mFABContainerTwo.animate().translationY(-getResources().getDimension(R.dimen.second_fab_translation));
        mFABContainerThree.animate().translationY(-getResources().getDimension(R.dimen.third_fab_translation));
    }

    private void hideFABMenu() {
        isFABOpen = false;

        // px is the amount of margin space we give the view in xml and half is half of the normal FAB's size.
        // This is roughly, ROUGHLY in the middle of the FAB for the circular reveal.
        Resources resources = getActivity().getResources();
        float px = resources.getDimensionPixelSize(R.dimen.fab_margin_bottom_right);
        float half = resources.getDimensionPixelSize(R.dimen.normal_fab_size) / 2;

        int cx = (int)(mSortingFAB.getX() + px);
        int cy = (int)(mSortingFAB.getY() + half - px);

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(mFABShadowOverlay, cx, cy, finalRadius, 0);

        // make the view gone when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFABShadowOverlay.setVisibility(View.GONE);
                mSortingFAB.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_reorder_white, null));
            }
        });

        // start the animation
        anim.start();

        mFABContainerOne.animate().translationY(0);
        mFABContainerTwo.animate().translationY(0);
        mFABContainerThree.animate().translationY(0);
    }

    /**
     * setup the recyclerview and tell it to listen for scroll changes and act accordingly.
     */
    private void initControls() {
        mLayoutManager = new WrapContentGridLayoutManager(getActivity(), 3);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
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
    public void updateAnimeList(List<SmallAnimeObject> animeList) {
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