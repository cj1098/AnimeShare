package cj1098.event;

import java.util.List;

import cj1098.animeshare.userList.AnimeObject;

/**
 * Created by chris on 12/26/16.
 */

public class BatchResponseFinishedEvent extends BaseEvent {
    private List<AnimeObject> animeList;

    public BatchResponseFinishedEvent(List<AnimeObject> animeList) {
        this.animeList = animeList;
    }

    public List<AnimeObject> getAnimeList() {
        return animeList;
    }
}
