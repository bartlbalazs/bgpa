package hu.bartl.bggprofileanalyzer.stats;

import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class DesignerPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public DesignerPopularityStatCalculator() {
        super(BoardGame::getDesigners);
    }
}
