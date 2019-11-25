package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

@Component
public class CardGamer extends GroupBasedBadge {

    private static final String ID = "cardGames";

    private static final int CARD_GAMES_ID = 1002;
    private static final int BADGE_THRESHOLD = 10;

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
        return CARD_GAMES_ID;
    }

    @Override
    protected Stats getGroup() {
        return Stats.CATEGORY;
    }
}
