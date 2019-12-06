package hu.bartl.bggprofileanalyzer.stats;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import hu.bartl.bggprofileanalyzer.service.XmlParser;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class RecommendedPlayerCountStatCalculatorTest {

    private RecommendedPlayerCountStatCalculator underTest;

    private Set<BoardGame> boardGames;

    @Before
    public void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
        underTest = new RecommendedPlayerCountStatCalculator(new StreamHelper());
    }

    @Test
    public void shouldCalculatePlayerCountStatistics() {
        List<Popularity> gamesByRecommendedPlayerCount = underTest.calculate(boardGames);
        assertThat(gamesByRecommendedPlayerCount.size(), is(7));
    }
}