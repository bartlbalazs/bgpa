package hu.bartl.bggprofileanalyzer.service;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithCoordinates;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import hu.bartl.bggprofileanalyzer.stats.BestPlayerCountStatCalculator;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class GamesToChartDataConverterTest {

    private GamesToChartDataConverter underTest;

    private Set<BoardGame> boardGames;

    @Before
    public void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
        underTest = new GamesToChartDataConverter(new StreamHelper());
    }

    @Test
    public void gamesToPlayercountComplexityChart() {
        List<NamedEntityWithCoordinates> result = underTest.gamesToPlayercountComplexityChart(boardGames);
        assertThat(result.size(), is(58));
    }
}