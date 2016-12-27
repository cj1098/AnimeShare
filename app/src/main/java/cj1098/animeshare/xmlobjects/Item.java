package cj1098.animeshare.xmlobjects;

public class Item {
    private String id;

    private String gid;

    private String type;

    private String name;

    private String precision;

    private String vintage;

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

    public void setVintage(String vintage) {
        this.vintage = vintage;
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

    public String getVintage() {
        return vintage;
    }
}