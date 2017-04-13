package cj1098.animeshare.userList;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "10",
        "20",
        "30",
        "40",
        "50",
        "60",
        "70",
        "80",
        "90",
        "100"
})
public class ScoreDistribution {

    @JsonProperty("10")
    private int _10;
    @JsonProperty("20")
    private int _20;
    @JsonProperty("30")
    private int _30;
    @JsonProperty("40")
    private int _40;
    @JsonProperty("50")
    private int _50;
    @JsonProperty("60")
    private int _60;
    @JsonProperty("70")
    private int _70;
    @JsonProperty("80")
    private int _80;
    @JsonProperty("90")
    private int _90;
    @JsonProperty("100")
    private int _100;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("10")
    public int get10() {
        return _10;
    }

    @JsonProperty("10")
    public void set10(int _10) {
        this._10 = _10;
    }

    @JsonProperty("20")
    public int get20() {
        return _20;
    }

    @JsonProperty("20")
    public void set20(int _20) {
        this._20 = _20;
    }

    @JsonProperty("30")
    public int get30() {
        return _30;
    }

    @JsonProperty("30")
    public void set30(int _30) {
        this._30 = _30;
    }

    @JsonProperty("40")
    public int get40() {
        return _40;
    }

    @JsonProperty("40")
    public void set40(int _40) {
        this._40 = _40;
    }

    @JsonProperty("50")
    public int get50() {
        return _50;
    }

    @JsonProperty("50")
    public void set50(int _50) {
        this._50 = _50;
    }

    @JsonProperty("60")
    public int get60() {
        return _60;
    }

    @JsonProperty("60")
    public void set60(int _60) {
        this._60 = _60;
    }

    @JsonProperty("70")
    public int get70() {
        return _70;
    }

    @JsonProperty("70")
    public void set70(int _70) {
        this._70 = _70;
    }

    @JsonProperty("80")
    public int get80() {
        return _80;
    }

    @JsonProperty("80")
    public void set80(int _80) {
        this._80 = _80;
    }

    @JsonProperty("90")
    public int get90() {
        return _90;
    }

    @JsonProperty("90")
    public void set90(int _90) {
        this._90 = _90;
    }

    @JsonProperty("100")
    public int get100() {
        return _100;
    }

    @JsonProperty("100")
    public void set100(int _100) {
        this._100 = _100;
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