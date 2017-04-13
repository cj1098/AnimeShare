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
        "completed",
        "on_hold",
        "dropped",
        "plan_to_watch",
        "watching"
})
public class ListStats {

    @JsonProperty("completed")
    private int completed;
    @JsonProperty("on_hold")
    private int onHold;
    @JsonProperty("dropped")
    private int dropped;
    @JsonProperty("plan_to_watch")
    private int planToWatch;
    @JsonProperty("watching")
    private int watching;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("completed")
    public int getCompleted() {
        return completed;
    }

    @JsonProperty("completed")
    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @JsonProperty("on_hold")
    public int getOnHold() {
        return onHold;
    }

    @JsonProperty("on_hold")
    public void setOnHold(int onHold) {
        this.onHold = onHold;
    }

    @JsonProperty("dropped")
    public int getDropped() {
        return dropped;
    }

    @JsonProperty("dropped")
    public void setDropped(int dropped) {
        this.dropped = dropped;
    }

    @JsonProperty("plan_to_watch")
    public int getPlanToWatch() {
        return planToWatch;
    }

    @JsonProperty("plan_to_watch")
    public void setPlanToWatch(int planToWatch) {
        this.planToWatch = planToWatch;
    }

    @JsonProperty("watching")
    public int getWatching() {
        return watching;
    }

    @JsonProperty("watching")
    public void setWatching(int watching) {
        this.watching = watching;
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