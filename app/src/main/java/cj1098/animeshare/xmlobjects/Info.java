package cj1098.animeshare.xmlobjects;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 12/26/16.
 */

public class Info {

    private String text;

    private String gid;

    private String type;

    private String lang;

    private String websiteUrl;

    private String thumbnailImageSrc;

    private String width;

    private String height;

    private List<Img> img;

    public void setText(String text) {
        this.text = text;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void setThumbnailImageSrc(String thumbnailImageSrc) {
        this.thumbnailImageSrc = thumbnailImageSrc;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setImg(List<Img> img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public String getGid() {
        return gid;
    }

    public String getType() {
        return type;
    }

    public String getLang() {
        return lang;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getThumbnailImageSrc() {
        return thumbnailImageSrc;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public List<Img> getImg() {
        return img;
    }
}
