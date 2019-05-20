package hu.bartl.bggprofileanalyzer.data.badge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.stats.Stats;

import java.util.List;
import java.util.Map;

public interface Badge {

    String getId();

    String getDisplayName();

    String getDescription();

    @JsonIgnore
    boolean isApplicable(Map<Stats, List<Popularity>> stats);
}
