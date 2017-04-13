package cj1098.animeshare.animelist;

import java.util.List;

import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.userList.SmallAnimeObject;
import cj1098.base.BaseMvp;
import cj1098.base.BaseView;

/**
 * Created by chris on 11/27/16.
 */

public interface AnimeListMvp extends BaseMvp {
    interface View extends BaseView {
        void updateAnimeList(List<SmallAnimeObject> animeList);

    }

    interface Presenter extends BaseMvp.Presenter<View>{
        void makeBatchCall(String page);

        void makeAuthRequest();

    }
}
