package cj1098.animeshare.animelist;

import cj1098.animeshare.userList.AnimeObject;
import cj1098.base.BasePresenter;
import cj1098.event.AnimeObjectReceivedEvent;
import cj1098.event.RxBus;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 11/27/16.
 */

public class AnimeListPresenter extends BasePresenter<AnimeListMvp.View> implements AnimeListMvp.Presenter{

    private CompositeSubscription compositeSubscription;

    public AnimeListPresenter() {
    }

    @Override
    public void subscribeToObservables() {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(RxBus.getInstance().register(AnimeObjectReceivedEvent.class, this::updateAnimeList));
    }

    @Override
    public void unsubscribeFromObservables() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    private void updateAnimeList(AnimeObjectReceivedEvent animeObjectReceivedEvent) {
        if (isAttached()) {
            getView().updateAnimeList(animeObjectReceivedEvent.getAnimeObject());
        }
    }
}
