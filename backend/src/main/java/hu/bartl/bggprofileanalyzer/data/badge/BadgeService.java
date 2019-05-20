package hu.bartl.bggprofileanalyzer.data.badge;

import hu.bartl.bggprofileanalyzer.data.Popularity;
import hu.bartl.bggprofileanalyzer.stats.Stats;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log
public class BadgeService {

    private Set<Badge> badges;

    public Set<Badge> getBadges(Map<Stats, List<Popularity>> stats) {
        return badges.stream()
                .filter(badge -> badge.isApplicable(stats))
                .collect(Collectors.toSet());
    }
}
