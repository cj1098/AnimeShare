package cj1098.event;

import cj1098.animeshare.userList.AnimeObject;

/**
 * Created by chris on 11/18/16.
 */

public class AnimeObjectReceivedEvent extends BaseEvent {

    AnimeObject animeObject;

    public AnimeObjectReceivedEvent(AnimeObject animeObject) {
        this.animeObject = animeObject;
    }

    public AnimeObject getAnimeObject() {
        return animeObject;
    }

}
