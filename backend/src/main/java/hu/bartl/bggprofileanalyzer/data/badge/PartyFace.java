package hu.bartl.bggprofileanalyzer.data.badge;

import org.springframework.stereotype.Component;

@Component
public class PartyFace extends SubdomainBadge {

    private static final String ID = "partyFace";

    private static final int PARTY_GAMES_DOMAIN = 5498;
    private static final int BADGE_THRESHOLD = 30;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected int getDomainThreshold() {
        return BADGE_THRESHOLD;
    }

    @Override
    protected int getDomainId() {
        return PARTY_GAMES_DOMAIN;
    }
}
