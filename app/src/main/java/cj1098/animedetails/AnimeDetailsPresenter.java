package cj1098.animedetails;

import android.support.annotation.NonNull;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.base.BasePresenter;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chris on 1/2/17.
 */

public class AnimeDetailsPresenter extends BasePresenter<AnimeDetailsMvp.View> implements AnimeDetailsMvp.Presenter {

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void subscribeToObservables() {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void unsubscribeFromObservables() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
    }

    @Override
    public void attachView(@NonNull AnimeDetailsMvp.View view) throws ViewAlreadyAttachedException {
        super.attachView(view);
    }
}
