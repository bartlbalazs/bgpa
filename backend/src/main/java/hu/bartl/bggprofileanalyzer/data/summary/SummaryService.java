package hu.bartl.bggprofileanalyzer.data.summary;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;

@Log
@AllArgsConstructor
@Service
public class SummaryService {

    private static final int EXPANSION_CATEGORY_ID = 1042;

    private static final int KICKSTARTER_GAMES_FAMILY_ID = 8374;

    public Summary summarize(Set<BoardGame> games) {

        long allItemsCount = games.size();

        long minutesToPlay = games.stream().filter(isExpansionPredicate().negate()).mapToInt(BoardGame::getMaxplaytime).sum();

        long expansionsCount = games.stream().filter(isExpansionPredicate()).count();

        long ksItemsCount = games.stream().filter(isKsPredicate()).count();

        return Summary.builder()
                .allItemsCount(allItemsCount)
                .expansionsCount(expansionsCount)
                .ksItemsCount(ksItemsCount)
                .minutesToPlay(minutesToPlay)
                .build();
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

    private Predicate<BoardGame> isKsPredicate() {
        return i -> {
            Set<NamedEntity> families = i.getFamilies();
            if (families == null || families.isEmpty()) {
                return false;
            }
            return families.stream().anyMatch(c -> KICKSTARTER_GAMES_FAMILY_ID == c.getId());
        };
    }
}
