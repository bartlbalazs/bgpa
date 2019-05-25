package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;

@Component
public class PartyFace extends GroupBasedBadge {

    private static final String ID = "partyFace";

    private static final int PARTY_GAMES_DOMAIN = 5498;
    private static final int BADGE_THRESHOLD = 30;

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
        return PARTY_GAMES_DOMAIN;
    }

    @Override
    protected Stats getGroup() {
        return SUBDOMAIN;
    }
}
