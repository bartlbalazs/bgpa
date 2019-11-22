package hu.bartl.bggprofileanalyzer.service;

import hu.bartl.bggprofileanalyzer.configuration.PresentationConfiguration;
import hu.bartl.bggprofileanalyzer.data.summary.SummaryPresentation;
import hu.bartl.bggprofileanalyzer.data.user.UserStats;
import hu.bartl.bggprofileanalyzer.data.user.UserStatsPresentation;
import hu.bartl.bggprofileanalyzer.stats.PopularityStatAggregator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PresentationService {

    private PopularityStatAggregator aggregator;

    private PresentationConfiguration configuration;

    private Badge2PresentationConverter badgeConverter;

    public UserStatsPresentation rawStatsToPresentation(UserStats rawStats) {
        UserStatsPresentation.UserStatsPresentationBuilder presentationBuilder = UserStatsPresentation.fromRawStats(rawStats).toBuilder();

        presentationBuilder.summary(SummaryPresentation.fromRawSummary(rawStats.getSummary()));

        presentationBuilder.badges(rawStats.getBadges()
                .stream()
                .map(b -> badgeConverter.convert(b))
                .collect(Collectors.toSet()));

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
                configuration.getOtherFamilyMaxRatio(),
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
