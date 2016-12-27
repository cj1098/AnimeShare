package cj1098.animeshare.home;

import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.DeviceUtil;
import cj1098.animeshare.util.XMLUtil;
import cj1098.animeshare.xmlobjects.Item;
import cj1098.base.BasePresenter;
import cj1098.event.InitialDatabaseStorageEventEnded;
import cj1098.event.InitialDatabaseStorageEventStarted;
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

    private CompositeSubscription mCompositeSubscription;
    private DeviceUtil mDeviceUtil;
    private DatabaseUtil mDatabaseUtil;
    private XMLUtil mXMLUtil;
    private RxBus mRxBus;

    public HomeHeadlessPresenter(DeviceUtil deviceUtil, DatabaseUtil databaseUtil, XMLUtil xmlUtil) {
        mDeviceUtil = deviceUtil;
        mDatabaseUtil = databaseUtil;
        mXMLUtil = xmlUtil;
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
        mCompositeSubscription.add(mRxBus.register(InitialXMLParseFinishedEvent.class, this::initialDBPopulation));
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

    private void initialDBPopulation(InitialXMLParseFinishedEvent initialXMLParseFinishedEvent) {
        List<Item> animeIdList = mXMLUtil.parseInitialFullList(initialXMLParseFinishedEvent.getmAnimeIdList());
        mDatabaseUtil.setupInitialAnimeFetchData(animeIdList);
    }
}
