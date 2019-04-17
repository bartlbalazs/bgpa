package hu.bartl.bggprofileanalyzer.stats;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import hu.bartl.bggprofileanalyzer.data.UserStats;

import static java.lang.Math.toIntExact;
import static java.util.Comparator.comparingInt;

@AllArgsConstructor
public abstract class AbstractPopularityStatCalculator {
    
    private final Function<BoardGame, Set<NamedEntity>> evaluatedPropertyFunction;
    
    public List<UserStats.Popularity> calculate(Collection<BoardGame> boardGames) {
        
        Map<NamedEntity, Long> popularities = boardGames.stream()
                                                        .map(evaluatedPropertyFunction)
                                                        .flatMap(Collection::stream)
                                                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        return popularities.entrySet().stream()
                           .map(c -> mapToPopularity(c, boardGames.size()))
                           .sorted(comparingInt(UserStats.Popularity::getGamesCount).reversed())
                           .collect(Collectors.toList());
    }
    
    private UserStats.Popularity mapToPopularity(Map.Entry<NamedEntity, Long> entry, int allGamesCount) {
        return UserStats.Popularity.builder()
                                   .entityId(entry.getKey().getId())
                                   .entityName(entry.getKey().getName())
                                   .gamesCount(toIntExact(entry.getValue()))
                                   .gamesRatio(entry.getValue() * 100.0 / allGamesCount)
                                   .build();
    }
}
