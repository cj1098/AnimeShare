package cj1098.animeshare.userList;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 12/31/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title_romaji",
        "title_english",
        "title_japanese",
        "type",
        "series_type",
        "start_date",
        "end_date",
        "start_date_fuzzy",
        "end_date_fuzzy",
        "season",
        "description",
        "adult",
        "average_score",
        "popularity",
        "favourite",
        "image_url_sml",
        "image_url_med",
        "image_url_lge",
        "image_url_banner",
        "genres",
        "synonyms",
        "youtube_id",
        "hashtag",
        "updated_at",
        "score_distribution",
        "list_stats",
        "total_episodes",
        "duration",
        "airing_status",
        "source",
        "classification",
        "airing_stats"
})
public class SearchAnimeObject extends AnimeObject{

    @JsonProperty("id")
    private int id;
    @JsonProperty("title_romaji")
    private String titleRomaji;
    @JsonProperty("title_english")
    private String titleEnglish;
    @JsonProperty("title_japanese")
    private String titleJapanese;
    @JsonProperty("type")
    private String type;
    @JsonProperty("series_type")
    private String seriesType;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("start_date_fuzzy")
    private int startDateFuzzy;
    @JsonProperty("end_date_fuzzy")
    private int endDateFuzzy;
    @JsonProperty("season")
    private int season;
    @JsonProperty("description")
    private String description;
    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("average_score")
    private float averageScore;
    @JsonProperty("popularity")
    private int popularity;
    @JsonProperty("favourite")
    private boolean favourite;
    @JsonProperty("image_url_sml")
    private String imageUrlSml;
    @JsonProperty("image_url_med")
    private String imageUrlMed;
    @JsonProperty("image_url_lge")
    private String imageUrlLge;
    @JsonProperty("image_url_banner")
    private String imageUrlBanner;
    @JsonProperty("genres")
    private List<String> genres = null;
    @JsonProperty("synonyms")
    private List<Object> synonyms = null;
    @JsonProperty("youtube_id")
    private String youtubeId;
    @JsonProperty("hashtag")
    private String hashtag;
    @JsonProperty("updated_at")
    private int updatedAt;
    @JsonProperty("score_distribution")
    private ScoreDistribution scoreDistribution;
    @JsonProperty("list_stats")
    private ListStats listStats;
    @JsonProperty("total_episodes")
    private int totalEpisodes;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("airing_status")
    private String airingStatus;
    @JsonProperty("source")
    private String source;
    @JsonProperty("classification")
    private String classification;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("title_romaji")
    public String getTitleRomaji() {
        return titleRomaji;
    }

    @JsonProperty("title_romaji")
    public void setTitleRomaji(String titleRomaji) {
        this.titleRomaji = titleRomaji;
    }

    @JsonProperty("title_english")
    public String getTitleEnglish() {
        return titleEnglish;
    }

    @JsonProperty("title_english")
    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    @JsonProperty("title_japanese")
    public String getTitleJapanese() {
        return titleJapanese;
    }

    @JsonProperty("title_japanese")
    public void setTitleJapanese(String titleJapanese) {
        this.titleJapanese = titleJapanese;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("series_type")
    public String getSeriesType() {
        return seriesType;
    }

    @JsonProperty("series_type")
    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("end_date")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("start_date_fuzzy")
    public int getStartDateFuzzy() {
        return startDateFuzzy;
    }

    @JsonProperty("start_date_fuzzy")
    public void setStartDateFuzzy(int startDateFuzzy) {
        this.startDateFuzzy = startDateFuzzy;
    }

    @JsonProperty("end_date_fuzzy")
    public int getEndDateFuzzy() {
        return endDateFuzzy;
    }

    @JsonProperty("end_date_fuzzy")
    public void setEndDateFuzzy(int endDateFuzzy) {
        this.endDateFuzzy = endDateFuzzy;
    }

    @JsonProperty("season")
    public int getSeason() {
        return season;
    }

    @JsonProperty("season")
    public void setSeason(int season) {
        this.season = season;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("adult")
    public boolean isAdult() {
        return adult;
    }

    @JsonProperty("adult")
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @JsonProperty("average_score")
    public float getAverageScore() {
        return averageScore;
    }

    @JsonProperty("average_score")
    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    @JsonProperty("popularity")
    public int getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("favourite")
    public boolean isFavourite() {
        return favourite;
    }

    @JsonProperty("favourite")
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @JsonProperty("image_url_sml")
    public String getImageUrlSml() {
        return imageUrlSml;
    }

    @JsonProperty("image_url_sml")
    public void setImageUrlSml(String imageUrlSml) {
        this.imageUrlSml = imageUrlSml;
    }

    @JsonProperty("image_url_med")
    public String getImageUrlMed() {
        return imageUrlMed;
    }

    @JsonProperty("image_url_med")
    public void setImageUrlMed(String imageUrlMed) {
        this.imageUrlMed = imageUrlMed;
    }

    @JsonProperty("image_url_lge")
    public String getImageUrlLge() {
        return imageUrlLge;
    }

    @JsonProperty("image_url_lge")
    public void setImageUrlLge(String imageUrlLge) {
        this.imageUrlLge = imageUrlLge;
    }

    @JsonProperty("image_url_banner")
    public String getImageUrlBanner() {
        return imageUrlBanner;
    }

    @JsonProperty("image_url_banner")
    public void setImageUrlBanner(String imageUrlBanner) {
        this.imageUrlBanner = imageUrlBanner;
    }

    @JsonProperty("genres")
    public List<String> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @JsonProperty("synonyms")
    public List<Object> getSynonyms() {
        return synonyms;
    }

    @JsonProperty("synonyms")
    public void setSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
    }

    @JsonProperty("youtube_id")
    public String getYoutubeId() {
        return youtubeId;
    }

    @JsonProperty("youtube_id")
    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    @JsonProperty("hashtag")
    public String getHashtag() {
        return hashtag;
    }

    @JsonProperty("hashtag")
    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    @JsonProperty("updated_at")
    public int getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("score_distribution")
    public ScoreDistribution getScoreDistribution() {
        return scoreDistribution;
    }

    @JsonProperty("score_distribution")
    public void setScoreDistribution(ScoreDistribution scoreDistribution) {
        this.scoreDistribution = scoreDistribution;
    }

    @JsonProperty("list_stats")
    public ListStats getListStats() {
        return listStats;
    }

    @JsonProperty("list_stats")
    public void setListStats(ListStats listStats) {
        this.listStats = listStats;
    }

    @JsonProperty("total_episodes")
    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    @JsonProperty("total_episodes")
    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    @JsonProperty("duration")
    public int getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @JsonProperty("airing_status")
    public String getAiringStatus() {
        return airingStatus;
    }

    @JsonProperty("airing_status")
    public void setAiringStatus(String airingStatus) {
        this.airingStatus = airingStatus;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    @JsonProperty("classification")
    public void setClassification(String classification) {
        this.classification = classification;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}