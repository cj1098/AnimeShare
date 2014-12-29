package cj1098.animeshare.userList;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chris on 12/28/14.
 */
public class ListItem implements Parcelable {

    private int id;
    private String slug;
    private String status;
    private String url;
    private String title;
    private String alternate_title;
    private int episode_count;
    private int episode_length;
    private String cover_image;
    private String synopsis;
    private String show_type;
    private String started_airing;
    private String finished_airing;
    private float community_rating;
    private String age_rating;
    private ArrayList<Name> genres;

    public ListItem() {
    }

    public ListItem(int id, String slug, String status, String url, String title, String alternate_title, int episode_count, int episode_length, String cover_image, String synopsis,
                    String show_type, String started_airing, String finished_airing, float community_rating, String age_rating, ArrayList<Name> genres) {
        this.id = id;
        this.slug = slug;
        this.status = status;
        this.url = url;
        this.title = title;
        this.alternate_title = alternate_title;
        this.episode_count = episode_count;
        this.episode_length = episode_length;
        this.cover_image = cover_image;
        this.synopsis = synopsis;
        this.show_type = show_type;
        this.started_airing = started_airing;
        this.finished_airing = finished_airing;
        this.community_rating = community_rating;
        this.age_rating = age_rating;
        this.genres = genres;
    }

    protected ListItem(Parcel in) {
        id = in.readInt();
        slug = in.readString();
        status = in.readString();
        url = in.readString();
        title = in.readString();
        alternate_title = in.readString();
        episode_count = in.readInt();
        episode_length = in.readInt();
        cover_image = in.readString();
        synopsis = in.readString();
        show_type = in.readString();
        started_airing = in.readString();
        finished_airing = in.readString();
        community_rating = in.readFloat();
        age_rating = in.readString();
        if (in.readByte() == 0x01) {
            genres = new ArrayList<Name>();
            in.readList(genres, Name.class.getClassLoader());
        } else {
            genres = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlternate_title() {
        return alternate_title;
    }

    public void setAlternative_title(String alternate_title) {
        this.alternate_title = alternate_title;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public int getEpisode_length() {
        return episode_length;
    }

    public void setEpisode_length(int episode_length) {
        this.episode_length = episode_length;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public String getStarted_airing() {
        return started_airing;
    }

    public void setStarted_airing(String started_airing) {
        this.started_airing = started_airing;
    }

    public String getFinished_airing() {
        return finished_airing;
    }

    public void setFinished_airing(String finished_airing) {
        this.finished_airing = finished_airing;
    }

    public Float getCommunity_rating() {
        return community_rating;
    }

    public void setCommunity_rating(float community_rating) {
        this.community_rating = community_rating;
    }

    public String getAge_rating() {
        return age_rating;
    }

    public void setAge_rating(String age_rating) {
        this.age_rating = age_rating;
    }

    public void setAlternate_title(String alternate_title) {
        this.alternate_title = alternate_title;
    }

    public ArrayList<Name> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Name> genres) {
        this.genres = genres;
    }

    private static class Name implements Serializable {
        private String name;

        public Name() {
        }

        public Name(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(slug);
        dest.writeString(status);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(alternate_title);
        dest.writeInt(episode_count);
        dest.writeInt(episode_length);
        dest.writeString(cover_image);
        dest.writeString(synopsis);
        dest.writeString(show_type);
        dest.writeString(started_airing);
        dest.writeString(finished_airing);
        dest.writeFloat(community_rating);
        dest.writeString(age_rating);
        if (genres == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genres);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListItem> CREATOR = new Parcelable.Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}
