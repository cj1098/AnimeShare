package cj1098.animedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import cj1098.animeshare.R;
import cj1098.animeshare.util.DaggerUtil;
import cj1098.base.BaseFragment;

/**
 * Created by chris on 1/2/17.
 */

public class AnimeDetailsFragment extends BaseFragment implements AnimeDetailsMvp.View {

    public static final String TAG = AnimeDetailsFragment.class.getSimpleName();

    @Inject
    AnimeDetailsMvp.Presenter mAnimeDetailsPresenter;

    public static AnimeDetailsFragment newInstance() {
        Bundle args = new Bundle();

        AnimeDetailsFragment fragment = new AnimeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_anime_details, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAnimeDetailsPresenter.attachView(this);
        mAnimeDetailsPresenter.subscribeToObservables();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAnimeDetailsPresenter.detachView();
        mAnimeDetailsPresenter.unsubscribeFromObservables();
    }
}
