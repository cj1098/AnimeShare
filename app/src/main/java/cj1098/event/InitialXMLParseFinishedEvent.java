package cj1098.event;

import java.util.List;

import cj1098.animeshare.xmlobjects.Item;

/**
 * Created by chris on 12/18/16.
 */

public class InitialXMLParseFinishedEvent extends BaseEvent {
    private String mAnimeIdList;

    public InitialXMLParseFinishedEvent(String idList) {
        mAnimeIdList = idList;
    }

    public String getmAnimeIdList() {
        return mAnimeIdList;
    }
}
