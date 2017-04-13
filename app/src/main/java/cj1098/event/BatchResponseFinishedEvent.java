package cj1098.event;

import java.util.List;

import cj1098.animeshare.userList.AnimeObject;
import cj1098.animeshare.userList.SmallAnimeObject;

/**
 * Created by chris on 12/26/16.
 */

public class BatchResponseFinishedEvent extends BaseEvent {
    private List<SmallAnimeObject> animeList;

    public BatchResponseFinishedEvent(List<SmallAnimeObject> animeList) {
        this.animeList = animeList;
    }

    public List<SmallAnimeObject> getAnimeList() {
        return animeList;
    }
}
