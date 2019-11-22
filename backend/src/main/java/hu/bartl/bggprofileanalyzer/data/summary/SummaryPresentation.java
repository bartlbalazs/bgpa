package hu.bartl.bggprofileanalyzer.data.summary;

import hu.bartl.bggprofileanalyzer.data.user.UserStats;
import hu.bartl.bggprofileanalyzer.data.user.UserStatsPresentation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryPresentation {

    private long gamesCount;

    private long expansionsCount;

    private long allItemsCount;

    private double averageGameWeight;

    long minutesToPlay;

    public static SummaryPresentation fromRawSummary(Summary summary) {
        return SummaryPresentation
                .builder()
                .gamesCount(summary.getGamesCount())
                .expansionsCount(summary.getExpansionsCount())
                .allItemsCount(summary.getGamesCount() + summary.getExpansionsCount())
                .averageGameWeight(summary.getAverageGameWeight())
                .minutesToPlay(summary.getMinutesToPlay())
                .build();
    }
}
