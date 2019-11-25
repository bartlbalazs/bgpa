package hu.bartl.bggprofileanalyzer.data.summary;

import com.google.common.collect.ImmutableSet;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class SummaryServiceTest {

    private SummaryService underTest;

    private static final int EXPANSION_CATEGORY_ID = 1042;
    private static final ImmutableSet<NamedEntity> EXPANSION = ImmutableSet.of(NamedEntity.builder().id(EXPANSION_CATEGORY_ID).build());

    @Before
    public void setup() {
        underTest = new SummaryService(new StreamHelper());
    }

    @Test
    public void shouldCountGamesAndExpansions() {
        BoardGame game1 = BoardGame.builder().id(1).build();
        BoardGame game2 = BoardGame.builder().id(2).build();
        BoardGame expansion = BoardGame.builder().id(3)
                .categories(EXPANSION)
                .build();

        Set<BoardGame> games = ImmutableSet.of(game1, game2, expansion);

        Summary summary = underTest.summarize(games);

        assertThat(summary.getGamesCount(), is(2L));
        assertThat(summary.getExpansionsCount(), is(1L));
    }

    @Test
    public void shouldCountTotalGamesTime() {
        BoardGame game1 = BoardGame.builder().id(1).maxplaytime(1).build();
        BoardGame game2 = BoardGame.builder().id(2).maxplaytime(3).build();
        BoardGame expansion = BoardGame.builder().id(3).maxplaytime(7)
                .categories(EXPANSION)
                .build();

        Set<BoardGame> games = ImmutableSet.of(game1, game2, expansion);

        Summary summary = underTest.summarize(games);

        assertThat(summary.getMinutesToPlay(), is(4L));
    }

    @Test
    public void shouldCalculateAvgGameWeight() {
        BoardGame game1 = BoardGame.builder().id(1).weight(1.5).build();
        BoardGame game2 = BoardGame.builder().id(2).weight(3.5).build();
        BoardGame expansion = BoardGame.builder().id(3).weight(5)
                .categories(EXPANSION)
                .build();

        Set<BoardGame> games = ImmutableSet.of(game1, game2, expansion);

        Summary summary = underTest.summarize(games);

        assertThat(summary.getAverageGameWeight(), is(2.5));
    }
}