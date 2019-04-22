package hu.bartl.bggprofileanalyzer.stats;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PopularityStatAggregator {

    private static final String OTHER_GROUP_NAME = "Other";

    public List<Popularity> aggregateSmallGroupsAsOthers(List<Popularity> rawPopularities, int otherGroupMaxPercentage, int minimal_group_size) {
        List<Popularity> result = Lists.newArrayList(rawPopularities);

        List<Integer> gamesInGroups = rawPopularities.stream()
                .map(p -> p.getGamesInGroup().size())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        result.add(Popularity.builder().entityName(OTHER_GROUP_NAME)
                .gamesInGroup(Sets.newHashSet())
                .gamesRatio(0.0)
                .build());

        for (int i = 0; i < gamesInGroups.size(); i++) {
            Integer gamesInSmallestGroups = gamesInGroups.get(i);

            Set<Popularity> smallGroups = rawPopularities.stream()
                    .filter(p -> p.getGamesInGroup().size() == gamesInSmallestGroups)
                    .collect(Collectors.toSet());

            smallGroups.add(findOtherGroup(result));

            Popularity extendedOtherGroup = aggregateGroupsAsOther(smallGroups);

            if (extendedOtherGroup.getGamesRatio() < otherGroupMaxPercentage) {
                result.removeAll(smallGroups);
                result.add(extendedOtherGroup);
            } else {
                break;
            }

        }

        Set<Popularity> smallerThanMinimalGroups = result.stream()
                .filter(g -> g.getGamesCount() < minimal_group_size)
                .collect(Collectors.toSet());

        if (smallerThanMinimalGroups.size() > 0) {
            aggregateGroupsAsOther(smallerThanMinimalGroups);
            result.removeAll(smallerThanMinimalGroups);
            result.add(aggregateGroupsAsOther(smallerThanMinimalGroups));
        }

        Popularity otherGroup = findOtherGroup(result);
        if (otherGroup.getGamesCount() == 0) {
            result.remove(otherGroup);
        }

        return result;
    }

    private Popularity findOtherGroup(List<Popularity> popularities) {
        return popularities.stream().filter(p -> OTHER_GROUP_NAME.equals(p.getEntityName())).findFirst().get();
    }

    private Popularity aggregateGroupsAsOther(Set<Popularity> smallGroups) {
        return smallGroups.stream().reduce((g1, g2) ->
                Popularity.builder().entityId(null).entityName(OTHER_GROUP_NAME)
                        .gamesInGroup(Sets.union(g1.getGamesInGroup(), g2.getGamesInGroup()))
                        .gamesRatio(g1.getGamesRatio() + g2.getGamesRatio())
                        .build()
        ).get();
    }
}
