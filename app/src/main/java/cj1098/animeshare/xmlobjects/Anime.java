package cj1098.animeshare.xmlobjects;

import java.util.List;

/**
 * Created by chris on 12/26/16.
 */

public class Anime {

    private String id;

    private String gid;

    private String type;

    private String name;

    private String precision;

    private String generatedOn;

    private RelatedPrev relatedPrev;

    private List<Info> infoList;

    private Ratings ratings;

    public void setId(String id) {
        this.id = id;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public void setGeneratedOn(String generatedOn) {
        this.generatedOn = generatedOn;
    }

    public void setRelatedPrev(RelatedPrev relatedPrev) {
        this.relatedPrev = relatedPrev;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    public String getId() {
        return id;
    }

    public String getGid() {
        return gid;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPrecision() {
        return precision;
    }

    public String getGeneratedOn() {
        return generatedOn;
    }

    public RelatedPrev getRelatedPrev() {
        return relatedPrev;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public Ratings getRatings() {
        return ratings;
    }
}
