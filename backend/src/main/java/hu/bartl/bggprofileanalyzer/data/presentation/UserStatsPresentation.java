package hu.bartl.bggprofileanalyzer.data.presentation;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.raw.UserStats;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class UserStatsPresentation {
    private final String userId;

    private final List<Popularity> categoryPopularities;
    private final List<Popularity> mechanicPopularities;
    private final List<Popularity> designerPopularities;
    private final List<Popularity> subDomainPopularities;
    private final List<Popularity> artistPopularities;
    private final List<Popularity> familyPopularities;

    public static UserStatsPresentation fromRawStats(UserStats userStats) {
        return UserStatsPresentation.builder()
                .userId(userStats.getUserId())
                .artistPopularities(userStats.getArtistPopularities())
                .categoryPopularities(userStats.getCategoryPopularities())
                .designerPopularities(userStats.getDesignerPopularities())
                .familyPopularities(userStats.getFamilyPopularities())
                .mechanicPopularities(userStats.getMechanicPopularities())
                .subDomainPopularities(userStats.getSubDomainPopularities())
                .build();
    }
}
