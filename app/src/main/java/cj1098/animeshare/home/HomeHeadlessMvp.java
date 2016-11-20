package cj1098.animeshare.home;

import cj1098.base.BaseMvp;
import cj1098.base.BaseView;

/**
 * Created by chris on 11/20/16.
 */

public interface HomeHeadlessMvp {

    interface View extends BaseView {
        void showNoNetworkDialog();

        void showSlowNetworkDialog();
    }

    interface Presenter extends BaseMvp.Presenter<View> {

    }
}
