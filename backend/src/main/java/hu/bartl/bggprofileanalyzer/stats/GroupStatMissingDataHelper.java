package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupStatMissingDataHelper {
    public List<Popularity> fillData(List<Popularity> weightPopularities, double step) {
        return weightPopularities;
    }
}
