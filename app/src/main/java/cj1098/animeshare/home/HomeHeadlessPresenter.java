package cj1098.animeshare.home;

import android.support.annotation.NonNull;

import java.util.List;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.DeviceUtil;
import cj1098.base.BasePresenter;
import cj1098.event.NoNetworkEvent;
import cj1098.event.RxBus;
import cj1098.event.SlowNetworkEvent;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 11/20/16.
 */

public class HomeHeadlessPresenter extends BasePresenter<HomeHeadlessMvp.View> implements
            HomeHeadlessMvp.Presenter {

    private CompositeSubscription mCompositeSubscription;
    private DeviceUtil mDeviceUtil;
    private DatabaseUtil mDatabaseUtil;
    private RxBus mRxBus;

    public HomeHeadlessPresenter(DeviceUtil deviceUtil, DatabaseUtil databaseUtil) {
        mDeviceUtil = deviceUtil;
        mDatabaseUtil = databaseUtil;
        mRxBus = RxBus.getInstance();
    }

    @Override
    public void attachView(@NonNull HomeHeadlessMvp.View view) throws ViewAlreadyAttachedException {
        super.attachView(view);
    }

    @Override
    public void subscribeToObservables() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(mRxBus.register(NoNetworkEvent.class, this::showNoNetworkDialog));
        mCompositeSubscription.add(mRxBus.register(SlowNetworkEvent.class, this::showSlowNetworkDialog));
    }

    @Override
    public void unsubscribeFromObservables() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }


    private void showNoNetworkDialog(NoNetworkEvent event) {
        if (isAttached()) {
            getView().showNoNetworkDialog();
        }
    }

    private void showSlowNetworkDialog(SlowNetworkEvent event) {
        if (isAttached()) {
            getView().showSlowNetworkDialog();
        }
    }

    private void initialDBPopulation() {
        //mDatabaseUtil.setupInitialAnimeFetchData(animeIdList);
    }
}
