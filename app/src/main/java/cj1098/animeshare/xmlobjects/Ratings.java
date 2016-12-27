package cj1098.animeshare.xmlobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by chris on 12/26/16.
 */

@Root(name = "ratings")
public class Ratings {

    @Attribute(name = "nb_votes", required = false)
    private String numOfVotes;

    @Attribute(name = "weighted_score", required = false)
    private String weightedScore;

    @Attribute(name = "bayesian_score", required = false)
    private String bayesianScore;

    public Ratings() {
    }

    public String getNumOfVotes() {
        return numOfVotes;
    }

    public String getWeightedScore() {
        return weightedScore;
    }

    public String getBayesianScore() {
        return bayesianScore;
    }
}
