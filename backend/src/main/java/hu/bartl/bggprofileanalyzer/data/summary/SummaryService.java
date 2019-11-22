package hu.bartl.bggprofileanalyzer.data.summary;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Log
@AllArgsConstructor
@Service
public class SummaryService {

    private static final int EXPANSION_CATEGORY_ID = 1042;

    private StreamHelper streams;

    public Summary summarize(Set<BoardGame> games) {


        long gamesCount = streams.getBoardGameStream(games).count();
        long expansionsCount = streams.getExpansionStream(games).count();
        long minutesToPlay = streams.getBoardGameStream(games).mapToInt(BoardGame::getMaxplaytime).sum();
        Double averageGameWeight = streams.getBoardGameStream(games).collect(Collectors.averagingDouble(BoardGame::getWeight));

        return Summary.builder()
                .gamesCount(gamesCount)
                .expansionsCount(expansionsCount)
                .minutesToPlay(minutesToPlay)
                .averageGameWeight(averageGameWeight)
                .build();
    }
}
