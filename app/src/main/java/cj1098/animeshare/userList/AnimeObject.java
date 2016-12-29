package cj1098.animeshare.userList;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 12/28/14.
 */
// TODO: May not need to be parcelable.
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title_romaji",
        "title_english",
        "title_japanese",
        "type",
        "start_date_fuzzy",
        "end_date_fuzzy",
        "season",
        "series_type",
        "synonyms",
        "genres",
        "adult",
        "average_score",
        "popularity",
        "updated_at",
        "image_url_sml",
        "image_url_med",
        "image_url_lge",
        "image_url_banner",
        "total_episodes",
        "airing_status",
        "tags"
})
public class AnimeObject {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title_romaji")
    private String titleRomaji;
    @JsonProperty("title_english")
    private String titleEnglish;
    @JsonProperty("title_japanese")
    private String titleJapanese;
    @JsonProperty("type")
    private String type;
    @JsonProperty("start_date_fuzzy")
    private Integer startDateFuzzy;
    @JsonProperty("end_date_fuzzy")
    private Integer endDateFuzzy;
    @JsonProperty("season")
    private Object season;
    @JsonProperty("series_type")
    private String seriesType;
    @JsonProperty("synonyms")
    private List<Object> synonyms = null;
    @JsonProperty("genres")
    private List<String> genres = null;
    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("average_score")
    private Double averageScore;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonProperty("updated_at")
    private Integer updatedAt;
    @JsonProperty("image_url_sml")
    private String imageUrlSml;
    @JsonProperty("image_url_med")
    private String imageUrlMed;
    @JsonProperty("image_url_lge")
    private String imageUrlLge;
    @JsonProperty("image_url_banner")
    private String imageUrlBanner;
    @JsonProperty("total_episodes")
    private Integer totalEpisodes;
    @JsonProperty("airing_status")
    private String airingStatus;
    @JsonProperty("tags")
    private List<Tag> tags = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public AnimeObject() {
    }

    private static class Tag {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("spoiler")
        private Boolean spoiler;
        @JsonProperty("adult")
        private Boolean adult;
        @JsonProperty("demographic")
        private Boolean demographic;
        @JsonProperty("denied")
        private Integer denied;
        @JsonProperty("category")
        private String category;
        @JsonProperty("votes")
        private Integer votes;
        @JsonProperty("series_spoiler")
        private String seriesSpoiler;

        public Tag() {
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Boolean getSpoiler() {
            return spoiler;
        }

        public Boolean getAdult() {
            return adult;
        }

        public Boolean getDemographic() {
            return demographic;
        }

        public Integer getDenied() {
            return denied;
        }

        public String getCategory() {
            return category;
        }

        public Integer getVotes() {
            return votes;
        }

        public String getSeriesSpoiler() {
            return seriesSpoiler;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getTitleRomaji() {
        return titleRomaji;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public String getTitleJapanese() {
        return titleJapanese;
    }

    public String getType() {
        return type;
    }

    public Integer getStartDateFuzzy() {
        return startDateFuzzy;
    }

    public Integer getEndDateFuzzy() {
        return endDateFuzzy;
    }

    public Object getSeason() {
        return season;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public List<Object> getSynonyms() {
        return synonyms;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Boolean getAdult() {
        return adult;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public String getImageUrlSml() {
        return imageUrlSml;
    }

    public String getImageUrlMed() {
        return imageUrlMed;
    }

    public String getImageUrlLge() {
        return imageUrlLge;
    }

    public String getImageUrlBanner() {
        return imageUrlBanner;
    }

    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }

    public String getAiringStatus() {
        return airingStatus;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}
