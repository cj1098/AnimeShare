package cj1098.animeshare.xmlobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by chris on 12/26/16.
 */

@Root(name = "related-prev", strict = false)
public class RelatedPrev {

    @Attribute(name = "rel")
    private String relation;

    @Attribute(name = "id")
    private String relatedId;

    public RelatedPrev() {
    }

    public String getRelation() {
        return relation;
    }

    public String getRelatedId() {
        return relatedId;
    }
}
