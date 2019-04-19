package hu.bartl.bggprofileanalyzer.stats;

import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class CategoryPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public CategoryPopularityStatCalculator() {
        super(BoardGame::getCategories);
    }
}
