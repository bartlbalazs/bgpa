package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;

@Component
public class Warlord extends GroupBasedBadge {

    private static final String ID = "warlord";

    private static final int WAR_GAMES_DOMAIN = 4664;
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
        return WAR_GAMES_DOMAIN;
    }

    @Override
    protected Stats getGroup() {
        return SUBDOMAIN;
    }
}
