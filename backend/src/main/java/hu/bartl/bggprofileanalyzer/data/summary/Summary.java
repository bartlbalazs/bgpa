package hu.bartl.bggprofileanalyzer.data.summary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Summary {

    private long gamesCount;

    private long expansionsCount;

    private double averageGameWeight;

    long minutesToPlay;
}
