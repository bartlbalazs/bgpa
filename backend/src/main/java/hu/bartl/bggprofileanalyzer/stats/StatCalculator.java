package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import java.util.Collection;
import java.util.List;

public interface StatCalculator {

    List<Popularity> calculate(Collection<BoardGame> boardGames);
}
