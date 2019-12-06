package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class YearPopularityStatCalculator implements StatCalculator {

    private StreamHelper streams;

    public YearPopularityStatCalculator(StreamHelper streams) {
        this.streams = streams;
    }

    @Override
    public List<Popularity> calculate(Collection<BoardGame> boardGames) {
        Map<Integer, List<BoardGame>> gamesByYears = boardGames.stream()
                .collect(Collectors.groupingBy(BoardGame::getYearpublished));

        return gamesByYears.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getKey))
                .map(yGs -> Popularity.builder()
                        .entityId(Optional.empty())
                        .entityName(String.valueOf(yGs.getKey()))
                        .gamesInGroup(yGs.getValue()
                                .stream()
                                .map(g -> streams.namedEntityWithPictureFromGame(g))
                                .collect(Collectors.toSet()))
                        .gamesRatio(yGs.getValue().size() * 100.0 / boardGames.size())
                        .build()
                ).collect(Collectors.toList());
    }
}
