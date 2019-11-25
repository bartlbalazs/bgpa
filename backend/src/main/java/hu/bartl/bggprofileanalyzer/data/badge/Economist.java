package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

@Component
public class Economist extends GroupBasedBadge {

    private static final String ID = "economist";

    private static final int ECONOMIC_GAMES_ID = 1021;
    private static final int BADGE_THRESHOLD = 12;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected int getGroupThreshold() {
        return BADGE_THRESHOLD;
    }

    @Override
    protected int getGroupItemId() {
        return ECONOMIC_GAMES_ID;
    }

    @Override
    protected Stats getGroup() {
        return Stats.CATEGORY;
    }
}
