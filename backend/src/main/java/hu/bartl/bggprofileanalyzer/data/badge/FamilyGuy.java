package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;

@Component
public class FamilyGuy extends GroupBasedBadge {

    private static final String ID = "familyGuy";

    private static final int FAMILY_GAMES_DOMAIN = 5499;
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
        return FAMILY_GAMES_DOMAIN;
    }

    @Override
    protected Stats getGroup() {
        return SUBDOMAIN;
    }
}
