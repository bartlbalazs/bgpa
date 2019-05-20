package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.stats.Stats;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;

@Component
public class Polyhistor implements Badge {

    private static final String ID = "polyhistor";
    private static final int BADGE_THRESHOLD = 40;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDisplayName() {
        return "Polyhistor";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isApplicable(Map<Stats, List<Popularity>> stats) {
        return !stats.get(SUBDOMAIN).stream().filter(s -> s.getGamesRatio() > BADGE_THRESHOLD).findAny().isPresent();
    }
}
