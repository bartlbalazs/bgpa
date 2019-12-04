package hu.bartl.bggprofileanalyzer.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PlayerCountStatCalculator implements StatCalculator {

    private StreamHelper streams;

    public PlayerCountStatCalculator(StreamHelper streams) {
        this.streams = streams;
    }

    @Override
    public List<Popularity> calculate(Collection<BoardGame> boardGames) {
        long gamesCount = streams.getBoardGameStream(boardGames).count();
        Set<BoardGame> games = streams.getBoardGameStream(boardGames).collect(Collectors.toSet());

        Map<Integer, Set<BoardGame>> recommendations = Maps.newHashMap();

        for (BoardGame boardGame : games) {
            boardGame.getPlayerCountRecommendations()
                .forEach(r -> {
                    if (r.isRecommended()) {
                        Set<BoardGame> recommendation = recommendations.getOrDefault(r.getNumplayers(), Sets.newHashSet());
                        recommendation.add(boardGame);
                        recommendations.put(r.getNumplayers(), recommendation);
                    }
                });
        }

        return recommendations.entrySet().stream()
            .sorted(Comparator.comparingInt(Map.Entry::getKey))
            .map(r -> Popularity.builder()
                .entityId(Optional.empty())
                .entityName(String.valueOf(r.getKey()))
                .gamesInGroup(r.getValue()
                    .stream()
                    .map(g -> streams.namedEntityWithPictureFromGame(g))
                    .collect(Collectors.toSet()))
                .gamesRatio(r.getValue().size() * 100.0 / gamesCount)
                .build()
            ).collect(Collectors.toList());
    }
}
