package cj1098.animeshare.xmlobjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by chris on 12/26/16.
 */

@Root(name = "ann", strict = false)
public class BatchResponse {

    @ElementList(inline = true)
    private List<Anime> animeList;

    public BatchResponse() {
    }

    public List<Anime> getAnimeList() {
        return animeList;
    }
}
