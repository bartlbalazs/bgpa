package hu.bartl.bggprofileanalyzer.data.user;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.badge.Badge;
import hu.bartl.bggprofileanalyzer.data.summary.Summary;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserStats {

    private final String userId;
    private final Summary summary;
    private final Set<Badge> badges;
    private final Set<BoardGame> games;

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
}
