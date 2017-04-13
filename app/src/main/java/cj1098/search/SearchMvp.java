package cj1098.search;

import java.util.List;

import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.userList.SearchAnimeObject;
import cj1098.animeshare.userList.SmallAnimeObject;
import cj1098.base.BaseMvp;
import cj1098.base.BaseView;

/**
 * Created by chris on 12/31/16.
 */

public interface SearchMvp extends BaseMvp {

    interface View extends BaseView {
        void updateAnimeList(List<SearchAnimeObject> smallAnimeObject);
    }

    interface Presenter extends BaseMvp.Presenter<View> {
        void makeSearchCall(String name);
    }
}
