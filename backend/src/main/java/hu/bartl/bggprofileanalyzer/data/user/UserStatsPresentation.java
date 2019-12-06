package hu.bartl.bggprofileanalyzer.data.user;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.badge.BadgePresentation;
import hu.bartl.bggprofileanalyzer.data.summary.SummaryPresentation;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class UserStatsPresentation {
    private final String userId;
    private final SummaryPresentation summary;

    private final Set<BadgePresentation> badges;
    private final List<Popularity> categoryPopularities;
    private final List<Popularity> mechanismPopularities;
    private final List<Popularity> designerPopularities;
    private final List<Popularity> subDomainPopularities;
    private final List<Popularity> artistPopularities;
    private final List<Popularity> familyPopularities;


    private final List<Popularity> categoryWoExpansionsPopularities;
    private final List<Popularity> mechanismWoExpansionsPopularities;
    private final List<Popularity> designerWoExpansionsPopularities;
    private final List<Popularity> subDomainWoExpansionsPopularities;
    private final List<Popularity> artistWoExpansionsPopularities;
    private final List<Popularity> familyWoExpansionsPopularities;

    private final List<Popularity> weightPopularities;
    private final List<Popularity> recommendedPlayerCountPopularities;
    private final List<Popularity> bestPlayerCountPopularities;

    public static UserStatsPresentation fromRawStats(UserStats userStats) {
        return UserStatsPresentation.builder()
                .userId(userStats.getUserId())
                .artistPopularities(userStats.getArtistPopularities())
                .categoryPopularities(userStats.getCategoryPopularities())
                .designerPopularities(userStats.getDesignerPopularities())
                .familyPopularities(userStats.getFamilyPopularities())
                .mechanismPopularities(userStats.getMechanismPopularities())
                .subDomainPopularities(userStats.getSubDomainPopularities())
                .artistWoExpansionsPopularities(userStats.getArtistWoExpansionsPopularities())
                .categoryWoExpansionsPopularities(userStats.getCategoryWoExpansionsPopularities())
                .designerWoExpansionsPopularities(userStats.getDesignerWoExpansionsPopularities())
                .familyWoExpansionsPopularities(userStats.getFamilyWoExpansionsPopularities())
                .mechanismWoExpansionsPopularities(userStats.getMechanismWoExpansionsPopularities())
                .subDomainWoExpansionsPopularities(userStats.getSubDomainWoExpansionsPopularities())
                .weightPopularities(userStats.getWeightPopularities())
                .recommendedPlayerCountPopularities(userStats.getRecommendedPlayerCountPopularities())
                .bestPlayerCountPopularities(userStats.getBestPlayerCountPopularities())
                .build();
    }
}
