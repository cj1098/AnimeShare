package cj1098.event;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    private static final String TAG = RxBus.class.getSimpleName();

    private static RxBus sInstance = new RxBus();

    private Scheduler mObserveScheduler = AndroidSchedulers.mainThread();
    private final Subject<Object, Object> mBusSubject = new SerializedSubject<>(BehaviorSubject.create());

    public static RxBus getInstance() {
        if (sInstance == null) {
            synchronized (RxBus.class) {
                if (sInstance == null) {
                    sInstance = new RxBus();
                }
            }
        }

        return sInstance;
    }

    // Enforce singleton (sort of)
    @VisibleForTesting
    RxBus() {
    }

    // The below can be used to supply a mock bus
    @VisibleForTesting
    public static void setInstance(RxBus instance) {
        sInstance = instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseEvent> Subscription register(final Class<T> eventClass, Action1<T> onNext) {
        return mBusSubject
                .onBackpressureDrop()
                .filter(event -> eventClass.isAssignableFrom(event.getClass()))
                .map(obj -> (T) obj)
                .observeOn(mObserveScheduler)
                .subscribe(onNext);
    }

    public void post(@NonNull BaseEvent event) {
        Log.d(TAG, "RxBus event posted: " + event.getClass().getSimpleName());
        mBusSubject.onNext(event);
    }

    public void clear() {
        //mBusSubject.onNext(new NullEvent());
    }

    @VisibleForTesting
    public void setObserveScheduler(Scheduler scheduler) {
        mObserveScheduler = scheduler;
    }

    @VisibleForTesting
    public static void reset() {
        sInstance = null;
    }
}