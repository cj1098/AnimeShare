package cj1098.animeshare.home.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cj1098.animeshare.customViews.SpacesItemDecoration;
import cj1098.animeshare.customViews.WrapContentGridLayoutManager;
import cj1098.animeshare.R;
import cj1098.animeshare.userList.SearchAnimeObject;
import cj1098.animeshare.util.DaggerUtil;
import cj1098.animeshare.util.UiUtil;
import cj1098.base.BaseActivity;

/**
 * Created by chris on 12/30/16.
 */

public class SearchActivity extends BaseActivity implements SearchMvp.View {

    private static final String EMPTY_STRING = "";

    @Bind(R.id.back_to_home)
    Button mBackButton;

    @Bind(R.id.anime_search)
    EditText mAnimeSearch;

    @Bind(R.id.clear_anime_search)
    Button mClearAnimeSerach;

    @Bind(R.id.search_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.no_search_results_view)
    TextView mNoSearchResultsView;

    @Inject
    SearchMvp.Presenter mSearchPresenter;

    private RecyclerView.LayoutManager mLayoutManager;
    private SearchAnimeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
        setContentView(R.layout.activity_search_main);
        ButterKnife.bind(this);


        setupWindowAnimations();
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchPresenter.attachView(this);
        mSearchPresenter.subscribeToObservables();
    }

    @Override
    protected void onPause() {
        mSearchPresenter.detachView();
        mSearchPresenter.unsubscribeFromObservables();
        super.onPause();
    }

    private void setupViews() {
        setUpToolbar();
        setupRecyclerView();

    }

    private void setupWindowAnimations() {
        Transition enterTransition = new Fade();
        enterTransition.setDuration(1000);
        getWindow().setEnterTransition(enterTransition);
        getWindow().setExitTransition(enterTransition);
    }

    @Override
    public void updateAnimeList(List<SearchAnimeObject> animeList) {
        mAdapter.refreshListWithNewData(animeList);
        if (animeList.size() > 0) {
            mNoSearchResultsView.setVisibility(View.GONE);
        }
        else {
            mNoSearchResultsView.setVisibility(View.VISIBLE);
        }
    }

    private void setUpToolbar() {
        mAnimeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count >= 1 && charSequence.length() >= 1) {
                    // might need to throttle with Rx here.
                    mSearchPresenter.makeSearchCall(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.clear_anime_search)
    public void clearEditText() {
        mAnimeSearch.setText(EMPTY_STRING);
        mAdapter.clearSearchData();
        mNoSearchResultsView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.back_to_home)
    public void backToHome() {
        if (UiUtil.isKeyboardOpen(this)) {
            finish();
        }
        else {
            onBackPressed();
        }
    }

    private void setupRecyclerView() {
        mLayoutManager = new WrapContentGridLayoutManager(this, 3);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mRecyclerView.setLayoutManager(mLayoutManager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        mRecyclerView.setItemAnimator(itemAnimator);
        mAdapter = new SearchAnimeAdapter(this, new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }
}
