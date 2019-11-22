package hu.bartl.bggprofileanalyzer.data.summary;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@AllArgsConstructor
@Service
public class SummaryService {

    private static final int EXPANSION_CATEGORY_ID = 1042;

    public Summary summarize(Set<BoardGame> games) {


        long gamesCount = getBoardGameStream(games).count();
        long expansionsCount = games.stream().filter(isExpansionPredicate()).count();
        long minutesToPlay = getBoardGameStream(games).mapToInt(BoardGame::getMaxplaytime).sum();
        Double averageGameWeight = getBoardGameStream(games).collect(Collectors.averagingDouble(BoardGame::getWeight));

        return Summary.builder()
                .gamesCount(gamesCount)
                .expansionsCount(expansionsCount)
                .minutesToPlay(minutesToPlay)
                .averageGameWeight(averageGameWeight)
                .build();
    }

    private Stream<BoardGame> getBoardGameStream(Set<BoardGame> games) {
        return games.stream().filter(isExpansionPredicate().negate());
    }

    private Predicate<BoardGame> isExpansionPredicate() {
        return i -> {
            Set<NamedEntity> categories = i.getCategories();
            if (categories == null || categories.isEmpty()) {
                return false;
            }
            return categories.stream().anyMatch(c -> EXPANSION_CATEGORY_ID == c.getId());
        };
    }
}
