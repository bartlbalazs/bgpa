package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithPicture;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

@AllArgsConstructor
public abstract class AbstractPopularityStatCalculator {

    private final Function<BoardGame, Set<NamedEntity>> evaluatedPropertyFunction;

    public List<Popularity> calculate(Collection<BoardGame> boardGames) {

        Map<NamedEntity, Set<NamedEntityWithPicture>> popularities = boardGames.stream()
                .map(this::mapPropertiesToGameIds)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Pair::getFirst, Collectors.mapping(Pair::getSecond, Collectors.toSet())));


        return popularities.entrySet().stream()
                .map(c -> mapToPopularity(c, boardGames.size()))
                .sorted(comparingInt(Popularity::getGamesCount).reversed())
                .collect(Collectors.toList());
    }

    private Set<Pair<NamedEntity, NamedEntityWithPicture>> mapPropertiesToGameIds(BoardGame boardGame) {
        return evaluatedPropertyFunction.apply(boardGame)
                .stream()
                .filter(this::filterEntity)
                .map(p -> Pair.of(p, NamedEntityWithPicture.withPicture()
                        .id(boardGame.getId())
                        .name(boardGame.getName())
                        .thumbnail(boardGame.getThumbnail())
                        .build()))
                .collect(Collectors.toSet());
    }

    private Popularity mapToPopularity(Map.Entry<NamedEntity, Set<NamedEntityWithPicture>> entry, int allGamesCount) {
        return Popularity.builder()
                .entityId(entry.getKey().getId())
                .entityName(entry.getKey().getName())
                .gamesInGroup(entry.getValue())
                .gamesRatio(entry.getValue().size() * 100.0 / allGamesCount)
                .build();
    }

    protected boolean filterEntity(NamedEntity entity) {
        return true;
    }

    public abstract Stats getStatId();
}

