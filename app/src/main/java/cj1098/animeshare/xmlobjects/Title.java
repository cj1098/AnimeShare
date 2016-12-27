package cj1098.animeshare.xmlobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by chris on 12/26/16.
 */

@Root(name = "title")
public class Title {

    @Attribute(name = "gid" ,required = false)
    private String titleGid;

    @Attribute(name = "lang", required = false)
    private String lang;

    public Title() {

    }

    public String getTitleGid() {
        return titleGid;
    }

    public String getLang() {
        return lang;
    }
}
