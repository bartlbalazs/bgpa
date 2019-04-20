package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class FamilyPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public FamilyPopularityStatCalculator() {
        super(BoardGame::getFamilies);
    }

    private static final Long BETTER_DESCROPTION_NEEDED_ID = 22783L;

    @Override
    protected boolean filterEntity(NamedEntity entity) {
        return !BETTER_DESCROPTION_NEEDED_ID.equals(Long.valueOf(entity.getId()));
    }
}
