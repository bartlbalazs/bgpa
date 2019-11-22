package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithPicture;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WeightStatCalculator {
    //TODO
    //@Value("${app.weights.step}")
    private final double step = 0.2;

    private StreamHelper streams;

    private static final DecimalFormat WEIGHT_GROUP_NAME_FORMATTER = new DecimalFormat("#0.00");

    public List<Popularity> calculate(Collection<BoardGame> boardGames) {

        long gamesCount = streams.getBoardGameStream(boardGames).count();
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
                                .map(g -> NamedEntityWithPicture.withPicture()
                                        .id(g.getId())
                                        .name(g.getName())
                                        .thumbnail(g.getThumbnail())
                                        .build())
                                .collect(Collectors.toSet()))
                        .gamesRatio(rW.getValue().size() * 100.0 / gamesCount)
                        .build()
                ).collect(Collectors.toList());
    }
}
