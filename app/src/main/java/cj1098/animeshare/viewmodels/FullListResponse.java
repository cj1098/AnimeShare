package cj1098.animeshare.viewmodels;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by chris on 12/24/16.
 */

@Root(name = "report", strict = false)
public class FullListResponse {

    @ElementList(inline = true)
    private List<Item> itemList;


    public List<Item> getItemList() {
        return itemList;
    }
}
