package hu.bartl.bggprofileanalyzer.stats;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import hu.bartl.bggprofileanalyzer.service.XmlParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WeightStatCalculatorTest {

    public static final double DELTA_BETWEEN_WEIGHT_GROUPS = 0.2;
    private static Set<BoardGame> boardGames;

    @BeforeClass
    public static void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
    }

    @Test
    public void shouldCalculateWeightStats() {
        //TODO
        //WeightStatCalculator underTest = new WeightStatCalculator(DELTA_BETWEEN_WEIGHT_GROUPS, new StreamHelper());
        WeightStatCalculator underTest = new WeightStatCalculator(new StreamHelper());

        List<Popularity> weights = underTest.calculate(boardGames);
        assertThat(weights.get(0).getEntityId(), is(Optional.empty()));
        assertThat(weights.get(0).getEntityName(), is("1.20"));
        assertThat(weights.get(0).getGamesRatio(), closeTo(2.12, 0.01));
        assertThat(weights.get(0).getGamesCount(), is(1));
        assertThat(weights.get(0).getGamesInGroup().size(), is(1));
    }
}