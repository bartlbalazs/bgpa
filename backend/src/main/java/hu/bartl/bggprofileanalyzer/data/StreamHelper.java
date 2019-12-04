package hu.bartl.bggprofileanalyzer.data;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class StreamHelper {

    private static final int EXPANSION_CATEGORY_ID = 1042;

    public Stream<BoardGame> getBoardGameStream(Collection<BoardGame> games) {
        return games.stream().filter(isExpansionPredicate().negate());
    }

    public Stream<BoardGame> getExpansionStream(Collection<BoardGame> games) {
        return games.stream().filter(isExpansionPredicate());
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

    public NamedEntityWithPicture namedEntityWithPictureFromGame(BoardGame g) {
        return NamedEntityWithPicture.withPicture()
            .id(g.getId())
            .name(g.getName())
            .thumbnail(g.getThumbnail())
            .build();
    }
}
