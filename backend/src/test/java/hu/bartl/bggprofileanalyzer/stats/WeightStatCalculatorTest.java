package hu.bartl.bggprofileanalyzer.stats;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import hu.bartl.bggprofileanalyzer.service.XmlParser;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeightStatCalculatorTest {

    private static Set<BoardGame> boardGames;

    @Autowired
    private WeightStatCalculator underTest;

    @BeforeClass
    public static void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
    }

    @Test
    public void shouldCalculateWeightStats() {
        List<Popularity> weights = underTest.calculate(boardGames);
        assertThat(weights.get(5).getEntityId(), is(Optional.empty()));
        assertThat(weights.get(5).getEntityName(), is("1.25"));
        assertThat(weights.get(5).getGamesRatio(), closeTo(2.12, 0.01));
        assertThat(weights.get(5).getGamesCount(), is(1));
        assertThat(weights.get(5).getGamesInGroup().size(), is(1));
    }
}