package cj1098.animeshare.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import javax.inject.Inject;

import cj1098.animeshare.util.DaggerUtil;
import cj1098.base.BaseFragment;

/**
 * Created by chris on 11/18/16.
 */

public class HomeHeadlessFragment extends BaseFragment implements HomeHeadlessMvp.View {

    @Inject
    HomeHeadlessMvp.Presenter homeHeadlessPresenter;

    public static String TAG = HomeHeadlessFragment.class.getSimpleName();

    public static HomeHeadlessFragment newInstance() {
        return new HomeHeadlessFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUtil.getInstance().getApplicationComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeHeadlessPresenter.attachView(this);
        homeHeadlessPresenter.subscribeToObservables();
    }

    @Override
    public void onPause() {
        super.onPause();
        homeHeadlessPresenter.unsubscribeFromObservables();
        homeHeadlessPresenter.detachView();
    }

    @Override
    public void showNoNetworkDialog() {

    }

    @Override
    public void showSlowNetworkDialog() {

    }
}
