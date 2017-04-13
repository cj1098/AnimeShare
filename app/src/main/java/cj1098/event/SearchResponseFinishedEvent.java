package cj1098.event;

import java.util.List;

import cj1098.animeshare.userList.SearchAnimeObject;
import cj1098.animeshare.userList.SmallAnimeObject;

/**
 * Created by chris on 12/31/16.
 */

public class SearchResponseFinishedEvent extends BaseEvent {
    private List<SearchAnimeObject> animeList;

    public SearchResponseFinishedEvent(List<SearchAnimeObject> animeList) {
        this.animeList = animeList;
    }

    public List<SearchAnimeObject> getAnimeList() {
        return animeList;
    }
}
