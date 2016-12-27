package cj1098.animeshare.xmlobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by chris on 12/26/16.
 */

@Root(name = "img", strict = false)
public class Img {

    @Attribute(name = "src", required = false)
    private String imageSrc;

    @Attribute(name = "width", required = false)
    private String width;

    @Attribute(name = "height", required = false)
    private String height;

    public Img() {
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }
}
