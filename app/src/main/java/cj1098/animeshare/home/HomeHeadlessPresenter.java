package cj1098.animeshare.home;

import android.support.annotation.NonNull;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
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

    private CompositeSubscription compositeSubscription;
    private DeviceUtil deviceUtil;

    public HomeHeadlessPresenter(DeviceUtil deviceUtil) {
        this.deviceUtil = deviceUtil;
    }

    @Override
    public void attachView(@NonNull HomeHeadlessMvp.View view) throws ViewAlreadyAttachedException {
        super.attachView(view);
        if (!deviceUtil.isTablet()) {
            getView().showNoNetworkDialog();
        }
    }

    @Override
    public void subscribeToObservables() {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }

        compositeSubscription.add(RxBus.getInstance().register(NoNetworkEvent.class, this::showNoNetworkDialog));
        compositeSubscription.add(RxBus.getInstance().register(SlowNetworkEvent.class, this::showSlowNetworkDialog));
    }

    @Override
    public void unsubscribeFromObservables() {

    }


    private void showNoNetworkDialog(NoNetworkEvent event) {
        getView().showNoNetworkDialog();
    }

    private void showSlowNetworkDialog(SlowNetworkEvent event) {
        getView().showSlowNetworkDialog();
    }
}
