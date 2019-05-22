package hu.bartl.bggprofileanalyzer.data.badge;

import org.springframework.stereotype.Component;

@Component
public class FamilyGuy extends SubdomainBadge {

    private static final String ID = "familyGuy";

    private static final int FAMILY_GAMES_DOMAIN = 5499;
    private static final int BADGE_THRESHOLD = 40;

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
        return FAMILY_GAMES_DOMAIN;
    }
}
