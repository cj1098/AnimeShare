package cj1098.animeshare.animelist;

import cj1098.animeshare.home.HomeHeadlessMvp;
import cj1098.animeshare.userList.AnimeObject;
import cj1098.base.BaseMvp;
import cj1098.base.BaseView;

/**
 * Created by chris on 11/27/16.
 */

public interface AnimeListMvp extends BaseMvp {
    interface View extends BaseView {
        void updateAnimeList(AnimeObject animeObject);
    }

    interface Presenter extends BaseMvp.Presenter<View>{

    }
}
