package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.stats.Stats;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static hu.bartl.bggprofileanalyzer.stats.Stats.SUBDOMAIN;

public abstract class SubdomainBadge implements Badge {

    @Override
    public boolean isApplicable(Map<Stats, List<Popularity>> stats) {
        Optional<Popularity> subdomain = stats.get(SUBDOMAIN).stream().filter(i -> i.getEntityId() == getDomainId()).findFirst();
        return subdomain.isPresent() && subdomain.get().getGamesRatio() > getDomainThreshold();
    }

    protected abstract int getDomainThreshold();

    protected abstract int getDomainId();
}
