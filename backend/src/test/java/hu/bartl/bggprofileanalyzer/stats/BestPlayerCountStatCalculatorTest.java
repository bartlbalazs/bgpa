package hu.bartl.bggprofileanalyzer.stats;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import hu.bartl.bggprofileanalyzer.service.XmlParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BestPlayerCountStatCalculatorTest {

    private BestPlayerCountStatCalculator underTest;

    private Set<BoardGame> boardGames;

    @Before
    public void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
        underTest = new BestPlayerCountStatCalculator(new StreamHelper());
    }

    @Test
    public void shouldCalculatePlayerCountStatistics() {
        List<Popularity> gamesByRecommendedPlayerCount = underTest.calculate(boardGames);
        assertThat(gamesByRecommendedPlayerCount.size(), is(5));
    }
}