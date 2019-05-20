package hu.bartl.bggprofileanalyzer.data.badge;

import org.springframework.stereotype.Component;

@Component
public class StoryTeller extends SubdomainBadge {

    private static final String ID = "storyTeller";

    private static final int THEMATIC_GAMES_DOMAIN = 5496;
    private static final int BADGE_THRESHOLD = 40;

    @Override
    protected int getDomainThreshold() {
        return BADGE_THRESHOLD;
    }

    @Override
    protected int getDomainId() {
        return THEMATIC_GAMES_DOMAIN;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayName() {
        return "Storyteller";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
