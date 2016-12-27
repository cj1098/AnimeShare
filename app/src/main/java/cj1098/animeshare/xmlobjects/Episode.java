package cj1098.animeshare.xmlobjects;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by chris on 12/26/16.
 */

@Root(name = "episode")
public class Episode {

    @Attribute(name = "num", required = false)
    private String episodeNumber;

    @Element(name = "title", required = false)
    private Title title;

    public Episode() {
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public Title getTitle() {
        return title;
    }
}
