package hu.bartl.bggprofileanalyzer.stats;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import org.springframework.stereotype.Component;

@Component
public class SubDomainPopularityStatCalculator extends AbstractPopularityStatCalculator {

    public SubDomainPopularityStatCalculator() {
        super(BoardGame::getSubDomains);
    }

    @Override
    public Stats getStatId() {
        return Stats.SUBDOMAIN;
    }
}