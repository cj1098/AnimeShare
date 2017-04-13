package cj1098.animedetails;


import cj1098.base.BaseMvp;
import cj1098.base.BasePresenter;
import cj1098.base.BaseView;

/**
 * Created by chris on 1/2/17.
 */

public interface AnimeDetailsMvp {

    interface View extends BaseView {

    }

    interface Presenter extends BaseMvp.Presenter<View> {

    }
}
