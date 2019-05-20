package hu.bartl.bggprofileanalyzer.data.badge;

import org.springframework.stereotype.Component;

@Component
public class Warlord extends SubdomainBadge {

    private static final String ID = "warlord";

    private static final int WAR_GAMES_DOMAIN = 4664;
    private static final int BADGE_THRESHOLD = 40;

    @Override
    protected int getDomainThreshold() {
        return BADGE_THRESHOLD;
    }

    @Override
    protected int getDomainId() {
        return WAR_GAMES_DOMAIN;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayName() {
        return "Warlord";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
