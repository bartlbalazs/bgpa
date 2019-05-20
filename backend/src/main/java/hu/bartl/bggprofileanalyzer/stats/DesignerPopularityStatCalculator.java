package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import org.springframework.stereotype.Component;

@Component
public class DesignerPopularityStatCalculator extends AbstractPopularityStatCalculator {

    public DesignerPopularityStatCalculator() {
        super(BoardGame::getDesigners);
    }

    @Override
    public Stats getStatId() {
        return Stats.DESIGNER;
    }
}
