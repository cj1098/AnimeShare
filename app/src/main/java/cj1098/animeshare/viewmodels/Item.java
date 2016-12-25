package cj1098.animeshare.viewmodels;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
    public class Item {
        @Element(name = "id")
        private String id;

        @Element(name = "gid")
        private String gid;

        @Element(name = "type")
        private String type;

        @Element(name = "name")
        private String name;

        @Element(name = "precision")
        private String precision;

        @Element(name = "vintage", required = false)
        private String vintage;

    public Item() {
    }
}