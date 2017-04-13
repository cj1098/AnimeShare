package cj1098.event;

import cj1098.animeshare.userList.SmallAnimeObject;

/**
 * Created by chris on 11/18/16.
 */

public class AnimeObjectReceivedEvent extends BaseEvent {

    SmallAnimeObject smallAnimeObject;

    public AnimeObjectReceivedEvent(SmallAnimeObject smallAnimeObject) {
        this.smallAnimeObject = smallAnimeObject;
    }

    public SmallAnimeObject getSmallAnimeObject() {
        return smallAnimeObject;
    }

}
