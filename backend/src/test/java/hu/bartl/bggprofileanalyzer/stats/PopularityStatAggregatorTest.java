package hu.bartl.bggprofileanalyzer.stats;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PopularityStatAggregatorTest {

    private static PopularityStatAggregator underTest;

    @BeforeClass
    public static void setup() {
        underTest = new PopularityStatAggregator();
    }


    @Test
    public void shouldAggregateUntilOthersRatioIsLessThanSpecified() {
        Popularity G_40 = buildPopularity(40);
        Popularity G_22 = buildPopularity(22);
        Popularity G_18 = buildPopularity(18);
        Popularity G_7_A = buildPopularity(7);
        Popularity G_7_B = buildPopularity(7);
        Popularity G_5 = buildPopularity(6);

        List<Popularity> popularities = underTest.aggregateSmallGroupsAsOthers(
                Lists.newArrayList(G_40, G_22, G_18, G_7_A, G_7_B, G_5),
                30, 2);
        assertThat(popularities.size(), is(4));
        assertTrue(popularities.contains(G_40));
        assertTrue(popularities.contains(G_22));
        assertTrue(popularities.contains(G_18));
    }

    @Test
    public void shouldAggregateToOthersIfGroupSizeIsLessThanSpecified() {
        Popularity G_97 = buildPopularity(97);
        Popularity G_1_A = buildPopularity(1);
        Popularity G_1_B = buildPopularity(1);
        Popularity G_1_C = buildPopularity(1);

        List<Popularity> popularities = underTest.aggregateSmallGroupsAsOthers(
                Lists.newArrayList(G_97, G_1_A, G_1_B, G_1_C),
                30, 2);

        assertThat(popularities.size(), is(2));
    }

    @Test
    public void shouldNotCreateEmptyOthersGroup() {
        Popularity G_80 = buildPopularity(80);
        Popularity G_20 = buildPopularity(20);

        List<Popularity> popularities = underTest.aggregateSmallGroupsAsOthers(
                Lists.newArrayList(G_80, G_20),
                5, 1);

        assertThat(popularities.size(), is(2));
    }

    private Popularity buildPopularity(int ratio) {
        return Popularity.builder().entityId(ratio)
                .entityName("G_" + ratio).gamesInGroup(groupOfNGames(ratio)).gamesRatio(Double.valueOf(ratio)).build();
    }

    private Set<NamedEntity> groupOfNGames(int n) {
        Set<NamedEntity> result = Sets.newHashSet();
        for (int i = 0; i < n; i++) {
            result.add(NamedEntity.builder().id(i).name(String.valueOf(i)).build());
        }
        return result;
    }
}