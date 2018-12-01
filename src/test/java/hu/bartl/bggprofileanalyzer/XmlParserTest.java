package hu.bartl.bggprofileanalyzer;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import hu.bartl.bggprofileanalyzer.configuration.XmlParsingConfiguration;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.service.XmlParser;

import org.junit.Before;
import org.junit.Test;

import static hu.bartl.bggprofileanalyzer.TestHelper.getSingleBoardGame;
import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.junit.Assert.assertEquals;

public class XmlParserTest {
    
    private XmlMapper xmlMapper = new XmlParsingConfiguration().xmlMapper();
    private XmlParser underTest;
    
    @Before
    public void setup() {
        underTest = new XmlParser(xmlMapper);
    }
    
    @Test
    public void parseBoardGames() {
        Set<BoardGame> parsedBoardGames = underTest.parseBoardGames(getTestFileContent("single_board_game.xml"));
        HashSet<BoardGame> expected = new HashSet<>();
        
        BoardGame singleBoardGame = getSingleBoardGame();
        expected.add(singleBoardGame);
        assertEquals(expected, parsedBoardGames);
    }
}