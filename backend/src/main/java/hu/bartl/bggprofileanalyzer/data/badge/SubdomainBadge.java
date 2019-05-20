package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.stats.Stats;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class SubdomainBadge implements Badge {

    @Override
    public boolean isApplicable(Map<Stats, List<Popularity>> stats) {
        Optional<Popularity> subdomain = stats.get(Stats.SUBDOMAIN).stream().filter(i -> i.getEntityId() == getDomainId()).findFirst();
        return subdomain.isPresent() ? subdomain.get().getGamesRatio() > getDomainThreshold() : false;
    }

    protected abstract int getDomainThreshold();

    protected abstract int getDomainId();
}
