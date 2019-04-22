package hu.bartl.bggprofileanalyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import org.springframework.stereotype.Service;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.raw.UserProfile;
import hu.bartl.bggprofileanalyzer.data.raw.UserStats;
import hu.bartl.bggprofileanalyzer.stats.ArtistsPopularityStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.CategoryPopularityStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.DesignerPopularityStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.FamilyPopularityStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.MechanicPopularityStatCalculator;
import hu.bartl.bggprofileanalyzer.stats.SubDomainPopularityStatCalculator;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {
    
    private final ProfileDownloader profileDownloader;
    private final CategoryPopularityStatCalculator categoryPopularityStats;
    private final MechanicPopularityStatCalculator mechanicPopularityStats;
    private final DesignerPopularityStatCalculator designerPopularityStats;
    private final SubDomainPopularityStatCalculator subDomainPopularityStatCalculator;
    private final ArtistsPopularityStatCalculator artistPopularityStatCalculator;
    private final FamilyPopularityStatCalculator familyPopularityStatCalculator;
    
    public UserStats createStats(String userId) {
        UserProfile userProfile = profileDownloader.loadProfile(userId);
        Set<BoardGame> games = userProfile.getBoardGames();
        return UserStats.builder()
                        .userId(userProfile.getUserId())
                        .games(games)
                        .categoryPopularities(categoryPopularityStats.calculate(games))
                        .mechanismPopularities(mechanicPopularityStats.calculate(games))
                        .designerPopularities(designerPopularityStats.calculate(games))
                        .subDomainPopularities(subDomainPopularityStatCalculator.calculate(games))
                        .artistPopularities(artistPopularityStatCalculator.calculate(games))
                        .familyPopularities(familyPopularityStatCalculator.calculate(games))
                        .build();
    }
}
