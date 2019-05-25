package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.stats.Stats;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class GroupBasedBadge implements Badge {

    @Override
    public boolean isApplicable(Map<Stats, List<Popularity>> stats) {
        Optional<Popularity> subdomain = stats.get(getGroup()).stream().filter(i -> i.getEntityId() == getGroupItemId()).findFirst();
        return subdomain.isPresent() && subdomain.get().getGamesRatio() > getGroupThreshold();
    }

    protected abstract int getGroupThreshold();

    protected abstract int getGroupItemId();

    protected abstract Stats getGroup();
}
