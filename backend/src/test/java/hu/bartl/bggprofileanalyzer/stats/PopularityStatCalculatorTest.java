package hu.bartl.bggprofileanalyzer.stats;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.service.XmlParser;

import org.junit.BeforeClass;
import org.junit.Test;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PopularityStatCalculatorTest {
    
    private static Set<BoardGame> boardGames;
    
    @BeforeClass
    public static void setup() {
        XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
        XmlParser xmlParser = new XmlParser(xmlMapper);
        boardGames = xmlParser.parseBoardGames(getTestFileContent("bgg_sample_user_full_collection.xml"));
    }
    
    @Test
    public void calculateCategoryPopularities() {
        CategoryPopularityStatCalculator underTest = new CategoryPopularityStatCalculator();
        List<Popularity> result = underTest.calculate(boardGames);
        assertThat(result.get(0).getEntityName(), is("Economic"));
        assertThat(result.size(), is(46));
    }
    
    @Test
    public void calculateMechanicPopularities() {
        MechanicPopularityStatCalculator underTest = new MechanicPopularityStatCalculator();
        List<Popularity> result = underTest.calculate(boardGames);
        assertThat(result.get(0).getEntityName(), is("Hand Management"));
        assertThat(result.size(), is(35));
    }
    
    @Test
    public void calculateDesignerPopularities() {
        DesignerPopularityStatCalculator underTest = new DesignerPopularityStatCalculator();
        List<Popularity> result = underTest.calculate(boardGames);
        assertThat(result.get(0).getEntityName(), is("Martin Wallace"));
        assertThat(result.size(), is(58));
    }
    
    @Test
    public void calculateSubDomainPopularities() {
        SubDomainPopularityStatCalculator underTest = new SubDomainPopularityStatCalculator();
        List<Popularity> result = underTest.calculate(boardGames);
        assertThat(result.get(0).getEntityName(), is("Strategy Games"));
        assertThat(result.size(), is(5));
    }
    
    @Test
    public void calculateArtistPopularities() {
        ArtistsPopularityStatCalculator underTest = new ArtistsPopularityStatCalculator();
        List<Popularity> result = underTest.calculate(boardGames);
        assertThat(result.get(0).getEntityName(), is("Klemens Franz"));
        assertThat(result.size(), is(79));
    }
    
    @Test
    public void calculateFamilyPopularities() {
        FamilyPopularityStatCalculator underTest = new FamilyPopularityStatCalculator();
        List<Popularity> result = underTest.calculate(boardGames);
        assertThat(result.get(0).getEntityName(), is("Components: Miniatures"));
        assertThat(result.size(), is(85));
    }
}