package hu.bartl.bggprofileanalyzer.stats;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.service.XmlParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class YearPopularityStatCalculatorTest {

    private static Set<BoardGame> boardGames;

    @Autowired
    private YearPopularityStatCalculator underTest;

    @BeforeClass
    public static void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
    }

    @Test
    public void shouldCalculateWeightStats() {
        List<Popularity> gamesBySears = underTest.calculate(boardGames);
        assertThat(gamesBySears.size(), is(14));
        assertThat(gamesBySears.get(5).getEntityId(), is(Optional.empty()));
        assertThat(gamesBySears.get(5).getEntityName(), is("2009"));
        assertThat(gamesBySears.get(5).getGamesRatio(), closeTo(10.41, 0.01));
        assertThat(gamesBySears.get(5).getGamesCount(), is(5));
        assertThat(gamesBySears.get(5).getGamesInGroup().size(), is(5));
    }
}