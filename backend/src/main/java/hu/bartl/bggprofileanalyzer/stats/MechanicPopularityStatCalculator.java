package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import org.springframework.stereotype.Component;

@Component
public class MechanicPopularityStatCalculator extends AbstractPopularityStatCalculator {

    public MechanicPopularityStatCalculator() {
        super(BoardGame::getMechanics);
    }

    @Override
    public Stats getStatId() {
        return Stats.MECHANISM;
    }
}