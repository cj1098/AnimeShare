package cj1098.event;

import java.util.List;

import cj1098.animeshare.xmlobjects.Anime;

/**
 * Created by chris on 12/26/16.
 */

public class BatchResponseFinishedEvent extends BaseEvent {
    private String animeList;

    public BatchResponseFinishedEvent(String animeList) {
        this.animeList = animeList;
    }

    public String getAnimeList() {
        return animeList;
    }
}
