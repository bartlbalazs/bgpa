package hu.bartl.bggprofileanalyzer.service;

import hu.bartl.bggprofileanalyzer.configuration.PresentationConfiguration;
import hu.bartl.bggprofileanalyzer.data.presentation.UserStatsPresentation;
import hu.bartl.bggprofileanalyzer.data.raw.UserStats;
import hu.bartl.bggprofileanalyzer.stats.PopularityStatAggregator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PresentationService {

    private PopularityStatAggregator aggregator;

    private PresentationConfiguration configuration;

    public UserStatsPresentation rawStatsToPresentation(UserStats rawStats) {
        UserStatsPresentation.UserStatsPresentationBuilder presentationBuilder = UserStatsPresentation.fromRawStats(rawStats).toBuilder();

        presentationBuilder.artistPopularities(aggregator.aggregateSmallGroupsAsOthers(rawStats.getArtistPopularities(),
                configuration.getOtherArtistMaxRatio(),
                configuration.getMinimalGroupSize()));

        presentationBuilder.categoryPopularities(aggregator.aggregateSmallGroupsAsOthers(rawStats.getCategoryPopularities(),
                configuration.getOthersMaxRatio(),
                configuration.getMinimalGroupSize()));

        presentationBuilder.designerPopularities(aggregator.aggregateSmallGroupsAsOthers(rawStats.getDesignerPopularities(),
                configuration.getOtherDesignerMaxRatio(),
                configuration.getMinimalGroupSize()));

        presentationBuilder.familyPopularities(aggregator.aggregateSmallGroupsAsOthers(rawStats.getFamilyPopularities(),
                configuration.getOthersMaxRatio(),
                configuration.getMinimalGroupSize()));

        presentationBuilder.mechanismPopularities(aggregator.aggregateSmallGroupsAsOthers(rawStats.getMechanismPopularities(),
                configuration.getOtherMechanicMaxRatio(),
                configuration.getMinimalGroupSize()));

        presentationBuilder.subDomainPopularities(aggregator.aggregateSmallGroupsAsOthers(rawStats.getSubDomainPopularities(),
                configuration.getOtherSubdomainMaxRatio(),
                configuration.getMinimalSubdomainGroupSize()));

        return presentationBuilder.build();
    }
}
