package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class CategoryPopularityStatCalculator extends AbstractPopularityStatCalculator {


    public static final Long EXPANSION_CATEGORY_ID = 1042L;

    public CategoryPopularityStatCalculator() {
        super(BoardGame::getCategories);
    }

    @Override
    protected boolean filterEntity(NamedEntity entity) {
        return !EXPANSION_CATEGORY_ID.equals(Long.valueOf(entity.getId()));
    }
}
