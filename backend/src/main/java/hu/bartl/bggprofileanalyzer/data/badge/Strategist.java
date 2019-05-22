package hu.bartl.bggprofileanalyzer.data.badge;

import org.springframework.stereotype.Component;

@Component
public class Strategist extends SubdomainBadge {

    private static final String ID = "strategist";

    private static final int STRATEGY_GAMES_DOMAIN = 5497;
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
        return STRATEGY_GAMES_DOMAIN;
    }
}
