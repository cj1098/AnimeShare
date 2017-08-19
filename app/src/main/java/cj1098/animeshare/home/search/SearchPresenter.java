package cj1098.animeshare.home.search;


import cj1098.animeshare.service.AnimeRequestService;
import cj1098.animeshare.util.Preferences;
import cj1098.base.BasePresenter;
import cj1098.event.RxBus;
import cj1098.event.SearchResponseFinishedEvent;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 12/31/16.
 */

public class SearchPresenter extends BasePresenter<SearchMvp.View> implements SearchMvp.Presenter {

    private AnimeRequestService mService;
    private CompositeSubscription mCompositeSubscription;
    private RxBus mRxBus;
    private Preferences mPreferences;

    public SearchPresenter(AnimeRequestService requestService, Preferences preferences) {
        mService = requestService;
        mPreferences = preferences;
        mRxBus = RxBus.getInstance();
        // Since this is a sticky RxBus we need to clear the last event otherwise the registration will get hit.
        mRxBus.clear();
    }

    @Override
    public void makeSearchCall(String name) {
        mService.searchByAnimeTitle(name, mPreferences.getAccessToken());
    }

    @Override
    public void subscribeToObservables() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
            mCompositeSubscription.add(mRxBus.register(SearchResponseFinishedEvent.class, this::updateAnimeList));
        }
    }

    @Override
    public void unsubscribeFromObservables() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
    }

    private void updateAnimeList(SearchResponseFinishedEvent searchResponseFinishedEvent) {
        getView().updateAnimeList(searchResponseFinishedEvent.getAnimeList());
    }
}
