package hu.bartl.bggprofileanalyzer.service;

import com.google.common.collect.Maps;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import hu.bartl.bggprofileanalyzer.data.badge.BadgeService;
import hu.bartl.bggprofileanalyzer.data.summary.SummaryService;
import hu.bartl.bggprofileanalyzer.data.user.UserProfile;
import hu.bartl.bggprofileanalyzer.data.user.UserStats;
import hu.bartl.bggprofileanalyzer.stats.AbstractPopularityStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.BestPlayerCountStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.RecommendedPlayerCountStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.Stats;
import hu.bartl.bggprofileanalyzer.stats.WeightStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.YearPopularityStatCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static hu.bartl.bggprofileanalyzer.stats.Stats.ARTIST;
import static hu.bartl.bggprofileanalyzer.stats.Stats.ARTIST_WITHOUT_EXPANSIONS;
import static hu.bartl.bggprofileanalyzer.stats.Stats.CATEGORY;
import static hu.bartl.bggprofileanalyzer.stats.Stats.CATEGORY_WITHOUT_EXPANSIONS;
import static hu.bartl.bggprofileanalyzer.stats.Stats.DESIGNER;
import static hu.bartl.bggprofileanalyzer.stats.Stats.DESIGNER_WITHOUT_EXPANSIONS;
import static hu.bartl.bggprofileanalyzer.stats.Stats.FAMILY;
import static hu.bartl.bggprofileanalyzer.stats.Stats.FAMILY_WITHOUT_EXPANSIONS;
import static hu.bartl.bggprofileanalyzer.stats.Stats.MECHANISM;
import static hu.bartl.bggprofileanalyzer.stats.Stats.MECHANISM_WITHOUT_EXPANSIONS;
import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;
import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN_WITHOUT_EXPANSIONS;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {

    private final ProfileDownloader profileDownloader;

    private final Set<AbstractPopularityStatCalculator> statCalculators;

    private final StreamHelper streamHelper;

    private final BadgeService badgeService;

    private final SummaryService summaryService;

    private final WeightStatCalculator weightStatCalculator;

    private final YearPopularityStatCalculator yearPopularityStatCalculator;

    private final RecommendedPlayerCountStatCalculator recommendedPlayerCountStatCalculator;

    private final BestPlayerCountStatCalculator bestPlayerCountStatCalculator;

    public UserStats createStats(String userId) {

        UserProfile userProfile = profileDownloader.loadProfile(userId);
        Set<BoardGame> games = userProfile.getBoardGames();
        Set<BoardGame> gamesWoExpensions = streamHelper.getBoardGameStream(games).collect(Collectors.toSet());

        Map<Stats, List<Popularity>> stats = Maps.newHashMap();
        statCalculators.forEach(c -> stats.put(c.getStatId(), c.calculate(games)));
        statCalculators.forEach(c -> stats.put(Stats.withoutExpansions(c.getStatId()), c.calculate(gamesWoExpensions)));

        return UserStats.builder()
                .userId(userProfile.getUserId())
                .summary(summaryService.summarize(games))
                .games(games)
                .badges(badgeService.getBadges(stats))
                .categoryPopularities(stats.get(CATEGORY))
                .mechanismPopularities(stats.get(MECHANISM))
                .designerPopularities(stats.get(DESIGNER))
                .subDomainPopularities(stats.get(SUBDOMAIN))
                .artistPopularities(stats.get(ARTIST))
                .familyPopularities(stats.get(FAMILY))
                .categoryWoExpansionsPopularities(stats.get(CATEGORY_WITHOUT_EXPANSIONS))
                .mechanismWoExpansionsPopularities(stats.get(MECHANISM_WITHOUT_EXPANSIONS))
                .designerWoExpansionsPopularities(stats.get(DESIGNER_WITHOUT_EXPANSIONS))
                .subDomainWoExpansionsPopularities(stats.get(SUBDOMAIN_WITHOUT_EXPANSIONS))
                .artistWoExpansionsPopularities(stats.get(ARTIST_WITHOUT_EXPANSIONS))
                .familyWoExpansionsPopularities(stats.get(FAMILY_WITHOUT_EXPANSIONS))
                .weightPopularities(weightStatCalculator.calculate(games))
                .yearPopularities(yearPopularityStatCalculator.calculate(games))
                .recommendedPlayerCountPopularities(recommendedPlayerCountStatCalculator.calculate(games))
                .bestPlayerCountPopularities(bestPlayerCountStatCalculator.calculate(games))
                .build();
    }
}
