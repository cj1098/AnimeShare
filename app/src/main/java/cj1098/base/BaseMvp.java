package cj1098.base;

import android.support.annotation.NonNull;

import cj1098.animeshare.exceptions.ViewAlreadyAttachedException;
import cj1098.animeshare.exceptions.ViewNotAttachedException;

/**
 * Created by chris on 11/20/16.
 */

public interface BaseMvp {


    /**
     * All *Mvp.Presenter interfaces should extend this interface. See
     * {@link com.sirius.ui.splash.login.LoginMvp.Presenter LoginMvp.Presenter} for an example.
     *
     * Furthermore, {@link BasePresenter} implements this interface, and all presenters should extend
     * that abstract class, which provides important basic functionality. See
     * {@link com.sirius.ui.splash.login.LoginPresenter LoginPresenter} for an example.
     *
     * @param <T> The {@code View} we're parameterizing our subclass on. See
     *            {@link com.sirius.ui.splash.login.LoginMvp.View LoginMvp.View} for an example.
     */
    interface Presenter<T extends BaseView> {

        /**
         * All subscriptions should be created inside this method, which ought to be called <em>after</em>
         * {@link #attachView(T)}. Those subscriptions should be added to a
         * {@link rx.subscriptions.CompositeSubscription CompositeSubscription}.
         */
        void subscribeToObservables();

        /**
         * The {@link rx.subscriptions.CompositeSubscription CompositeSubscription} created in {@link #attachView(T)}
         * should be cleared here. This method ought to be called <em>before</em> {@link #detachView()}.
         */
        void unsubscribeFromObservables();

        /**
         * If this presenter is attached to a Fragment, call this method in {@code #onResume()}, <em>before</em>
         * calling {@link #subscribeToObservables()}. If this presenter is attached to another type of object,
         * such as an adapter, use your best judgment.
         */
        void attachView(@NonNull T view) throws ViewAlreadyAttachedException;

        /**
         * If this presenter is attached to a Fragment, call this method in {@code #onPause()}, <em>after</em>
         * calling {@link #unsubscribeFromObservables()}. If this presenter is attached to another type of object,
         * such as an adapter, use your best judgment.
         */
        void detachView() throws ViewNotAttachedException;
    }

    // TODO tsr: use this interface
    interface MultiViewPresenter<T extends BaseView> {

        void subscribeToObservables();

        void unsubscribeFromObservables();

        void attachView(String key, @NonNull T view) throws ViewAlreadyAttachedException;

        void detachView(String key) throws ViewNotAttachedException;

    }

}
