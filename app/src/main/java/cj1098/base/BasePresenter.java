package cj1098.base;

/**
 * Created by chris on 11/20/16.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.animeshare.exceptions.ViewNotAttachedException;

public abstract class BasePresenter<T extends BaseView> implements BaseMvp.Presenter<T> {

    private T mBaseView;

    @Override
    public abstract void subscribeToObservables();

    @Override
    public abstract void unsubscribeFromObservables();

    @Override
    public void attachView(@NonNull T view) throws ViewAlreadyAttachedException {
        if (mBaseView != null) {
            throw new ViewAlreadyAttachedException();
        }
        setBaseView(view);
    }

    @Override
    public void detachView() throws ViewNotAttachedException {
        if (mBaseView == null) {
            throw new ViewNotAttachedException();
        }
        setBaseView(null);
    }

    protected T getView() {
        if (mBaseView == null) {
            throw new ViewNotAttachedException();
        }
        return mBaseView;
    }

    private void setBaseView(T baseView) {
        Log.d("BasePresenter" + "Setting baseView on presenter:" + getClass().getSimpleName(), "view: " +
                (baseView == null ? "null" : baseView.getClass().getSimpleName()));
        mBaseView = baseView;
    }

    protected boolean isAttached() {
        return mBaseView != null;
    }
}
