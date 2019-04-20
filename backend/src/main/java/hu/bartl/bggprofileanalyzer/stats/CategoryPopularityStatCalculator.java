package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import org.springframework.stereotype.Component;

@Component
public class CategoryPopularityStatCalculator extends AbstractPopularityStatCalculator {

    public CategoryPopularityStatCalculator() {
        super(BoardGame::getCategories);
    }

}
