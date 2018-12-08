package hu.bartl.bggprofileanalyzer.stats;

import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class ArtistsPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public ArtistsPopularityStatCalculator() {
        super(BoardGame::getArtists);
    }
}
