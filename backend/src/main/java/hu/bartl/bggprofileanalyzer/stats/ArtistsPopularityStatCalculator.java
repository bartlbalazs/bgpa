package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import org.springframework.stereotype.Component;

@Component
public class ArtistsPopularityStatCalculator extends AbstractPopularityStatCalculator {

    public ArtistsPopularityStatCalculator() {
        super(BoardGame::getArtists);
    }

    @Override
    public Stats getStatId() {
        return Stats.ARTIST;
    }
}
