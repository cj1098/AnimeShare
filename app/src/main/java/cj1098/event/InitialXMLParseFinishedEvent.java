package cj1098.event;

import java.util.List;

import cj1098.animeshare.viewmodels.Item;

/**
 * Created by chris on 12/18/16.
 */

public class InitialXMLParseFinishedEvent extends BaseEvent {
    private List<Item> mAnimeIdList;

    public InitialXMLParseFinishedEvent(List<Item> idList) {
        mAnimeIdList = idList;
    }

    public List<Item> getmAnimeIdList() {
        return mAnimeIdList;
    }
}
