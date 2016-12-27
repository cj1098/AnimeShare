package cj1098.animeshare.animelist;

import java.util.List;

import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.util.DatabaseUtil;
import cj1098.animeshare.util.XMLUtil;
import cj1098.animeshare.xmlobjects.Anime;
import cj1098.base.BasePresenter;
import cj1098.event.AnimeObjectReceivedEvent;
import cj1098.event.BatchResponseFinishedEvent;
import cj1098.event.InitialDatabaseStorageEventEnded;
import cj1098.event.InitialDatabaseStorageEventStarted;
import cj1098.event.InitialXMLParseFinishedEvent;
import cj1098.event.RxBus;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 11/27/16.
 */

public class AnimeListPresenter extends BasePresenter<AnimeListMvp.View> implements AnimeListMvp.Presenter{

    private CompositeSubscription mCompositeSubscription;
    private RxBus mRxBus;
    private DatabaseUtil mDatabaseUtil;
    private XMLUtil mXMLUtil;

    public AnimeListPresenter(DatabaseUtil databaseUtil, XMLUtil xmlUtil) {
        mRxBus = RxBus.getInstance();
        mDatabaseUtil = databaseUtil;
        mXMLUtil = xmlUtil;
    }

    @Override
    public void subscribeToObservables() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(mRxBus.register(InitialXMLParseFinishedEvent.class, e -> initialDBStorageEventStarted()));
        mCompositeSubscription.add(mRxBus.register(InitialDatabaseStorageEventEnded.class, e -> initialDBStorageEventEnded()));
        mCompositeSubscription.add(mRxBus.register(BatchResponseFinishedEvent.class, this::updateAnimeList));
    }

    @Override
    public void unsubscribeFromObservables() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    // region AnimeListMvp presenter interface methods
    @Override
    public void makeBatchCall(int startingId) {
        if (isAttached()) {
            getView().makeBatchCallWithIds(mDatabaseUtil.getNextFiftyIds(startingId));
        }
    }

    // endregion

    private void updateAnimeList(BatchResponseFinishedEvent responseFinishedEvent) {
        if (isAttached()) {
            List<Anime> animeList = mXMLUtil.parseFiftyAnimeEntries(responseFinishedEvent.getAnimeList());
            getView().updateAnimeList(animeList);
        }
    }

    private void initialDBStorageEventStarted() {
        if (isAttached()) {
            getView().showInitialDBStorageDialog();
        }
    }

    private void initialDBStorageEventEnded() {
        if (isAttached()) {
            getView().dismissInitialDBStorageDialog();
        }
    }
}
