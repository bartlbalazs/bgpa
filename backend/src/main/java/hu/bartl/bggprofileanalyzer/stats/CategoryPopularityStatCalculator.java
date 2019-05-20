package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryPopularityStatCalculator extends AbstractPopularityStatCalculator {

    private static final Long EXPANSION_CATEGORY_ID = 1042L;

    public CategoryPopularityStatCalculator() {
        super(BoardGame::getCategories);
    }

    @Override
    protected boolean filterEntity(NamedEntity entity) {
        return !EXPANSION_CATEGORY_ID.equals(entity.getId());
    }

    @Override
    public Stats getStatId() {
        return Stats.CATEGORY;
    }
}
