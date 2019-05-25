package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

@Component
public class Fighter extends GroupBasedBadge {

    private static final String ID = "fighter";

    private static final int FIGHTING_GAMES_ID = 1046;
    private static final int BADGE_THRESHOLD = 15;

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
        return FIGHTING_GAMES_ID;
    }

    @Override
    protected Stats getGroup() {
        return Stats.CATEGORY;
    }
}
