package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;

@Component
public class Strategist extends GroupBasedBadge {

    private static final String ID = "strategist";

    private static final int STRATEGY_GAMES_DOMAIN = 5497;
    private static final int BADGE_THRESHOLD = 40;

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
        return STRATEGY_GAMES_DOMAIN;
    }

    @Override
    protected Stats getGroup() {
        return SUBDOMAIN;
    }
}
