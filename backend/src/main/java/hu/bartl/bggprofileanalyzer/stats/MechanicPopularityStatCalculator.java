package hu.bartl.bggprofileanalyzer.stats;

import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class MechanicPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public MechanicPopularityStatCalculator() {
        super(BoardGame::getMechanics);
    }
}