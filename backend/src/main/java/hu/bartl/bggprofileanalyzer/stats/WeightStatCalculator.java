package hu.bartl.bggprofileanalyzer.stats;

import com.google.common.collect.Lists;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithPicture;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeightStatCalculator implements StatCalculator{

    @Value("${app.weights.step:1.0}")
    private double step;
    @Value("${app.weights.from:0}")
    private int from;
    @Value("${app.weights.to:5}")
    private int to;

    private StreamHelper streams;

    public WeightStatCalculator(StreamHelper streams) {
        this.streams = streams;
    }

    private static final DecimalFormat WEIGHT_GROUP_NAME_FORMATTER = new DecimalFormat("#0.00");

    public List<Popularity> calculate(Collection<BoardGame> boardGames) {

        long gamesCount = streams.getBoardGameStream(boardGames).count();
        List<Popularity> gamesByWeight = getGamesByWeights(boardGames, gamesCount);
        return addWeightsWithoutGames(gamesByWeight);
    }

    private List<Popularity> getGamesByWeights(Collection<BoardGame> boardGames, long gamesCount) {
        Map<Double, List<BoardGame>> roundedWeights = streams.getBoardGameStream(boardGames)
            .map(g -> g.toBuilder()
                .weight((int) (g.getWeight() / step) * step)
                .build()
            )
            .collect(Collectors.groupingBy(BoardGame::getWeight));

        return roundedWeights.entrySet().stream()
            .sorted(Comparator.comparingDouble(Map.Entry::getKey))
            .map(rW -> Popularity.builder()
                .entityId(Optional.empty())
                .entityName(WEIGHT_GROUP_NAME_FORMATTER.format(rW.getKey()))
                .gamesInGroup(rW.getValue()
                    .stream()
                    .map(g -> streams.namedEntityWithPictureFromGame(g))
                    .collect(Collectors.toSet()))
                .gamesRatio(rW.getValue().size() * 100.0 / gamesCount)
                .build()
            ).collect(Collectors.toList());
    }

    private List<Popularity> addWeightsWithoutGames(List<Popularity> gamesByWeight) {
        List<Popularity> result = Lists.newArrayList();
        for (int i = from; i * step < to; i++) {
            String weightCategoryName = WEIGHT_GROUP_NAME_FORMATTER.format(i * step);
            result.add(gamesByWeight.stream()
                .filter(p -> p.getEntityName().equals(weightCategoryName))
                .findAny().orElse(Popularity
                    .builder()
                    .entityId(Optional.empty())
                    .entityName(weightCategoryName)
                    .gamesRatio(0.0)
                    .build()));
        }
        return result;
    }
}
