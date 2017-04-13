package cj1098.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by chris on 11/18/16.
 */

@UiThread
public class BaseFragment extends Fragment {

    public BaseDialogFragment mBaseDialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // All fragments that extend from this one will not be destroyed on rotation.
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    /**
     * Simple wrapper for {@code getActivity()}, which returns an instance of {@link BaseActivity}.
     *
     * @return An instance of {@code BaseActivity}.
     * @throws IllegalStateException if the {@code Activity} to which this fragment is attached is either
     *                               null or not an instance of {@code BaseActivity}.
     */
    @NonNull
    protected BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }

        String wrongClass = activity != null ? activity.getClass().getSimpleName() : "null";
        throw new IllegalStateException(String.format("%s is attached to the wrong type of Activity <%s expected, was %s>",
                this.getClass().getSimpleName(), BaseActivity.class.getSimpleName(), wrongClass));
    }

    /**
     * Returns true if neither the Activity callback nor the Activity are null, and if the Activity is not finishing.
     *
     * @return true if neither the Activity callback nor the Activity are null, and if the Activity is not finishing.
     */
    public boolean isActivityValid() {
        return getActivity() != null && !getActivity().isFinishing();
    }
}
