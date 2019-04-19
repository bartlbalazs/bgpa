package hu.bartl.bggprofileanalyzer.stats;

import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class FamilyPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public FamilyPopularityStatCalculator() {
        super(BoardGame::getFamilies);
    }
}
