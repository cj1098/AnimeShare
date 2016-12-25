package cj1098.animeshare.home;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.DeviceUtil;
import cj1098.base.BasePresenter;
import cj1098.event.InitialXMLParseFinishedEvent;
import cj1098.event.NoNetworkEvent;
import cj1098.event.RxBus;
import cj1098.event.SlowNetworkEvent;
import rx.subscriptions.CompositeSubscription;

import static cj1098.base.BaseActivity.TAG;

/**
 * Created by chris on 11/20/16.
 */

public class HomeHeadlessPresenter extends BasePresenter<HomeHeadlessMvp.View> implements
            HomeHeadlessMvp.Presenter {

    private CompositeSubscription compositeSubscription;
    private DeviceUtil deviceUtil;
    private DatabaseUtil databaseUtil;

    public HomeHeadlessPresenter(DeviceUtil deviceUtil, DatabaseUtil databaseUtil) {
        this.deviceUtil = deviceUtil;
        this.databaseUtil = databaseUtil;
    }

    @Override
    public void attachView(@NonNull HomeHeadlessMvp.View view) throws ViewAlreadyAttachedException {
        super.attachView(view);
    }

    @Override
    public void subscribeToObservables() {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }

        compositeSubscription.add(RxBus.getInstance().register(NoNetworkEvent.class, this::showNoNetworkDialog));
        compositeSubscription.add(RxBus.getInstance().register(SlowNetworkEvent.class, this::showSlowNetworkDialog));
        compositeSubscription.add(RxBus.getInstance().register(InitialXMLParseFinishedEvent.class, this::initialDBPopulation));
    }

    @Override
    public void unsubscribeFromObservables() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
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

    private void initialDBPopulation(InitialXMLParseFinishedEvent initialXMLParseFinishedEvent) {
        databaseUtil.setupInitialAnimeFetchData(initialXMLParseFinishedEvent.getmAnimeIdList());
    }
}
