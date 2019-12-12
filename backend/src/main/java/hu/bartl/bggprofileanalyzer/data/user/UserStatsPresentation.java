package hu.bartl.bggprofileanalyzer.data.user;

import com.google.common.collect.Lists;
import hu.bartl.bggprofileanalyzer.data.ChartData;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithCoordinates;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.badge.BadgePresentation;
import hu.bartl.bggprofileanalyzer.data.summary.SummaryPresentation;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.util.Pair;

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
    private final List<Popularity> yearPopularities;
    private final List<Popularity> recommendedPlayerCountPopularities;
    private final List<Popularity> bestPlayerCountPopularities;

    private final List<ChartData>  gamesByComplexityAndPlayerCount;

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
            .yearPopularities(userStats.getYearPopularities())
            .recommendedPlayerCountPopularities(userStats.getRecommendedPlayerCountPopularities())
            .bestPlayerCountPopularities(userStats.getBestPlayerCountPopularities())
            .gamesByComplexityAndPlayerCount(Lists.newArrayList())
            .build();
    }
}
