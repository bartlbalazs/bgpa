package hu.bartl.bggprofileanalyzer.stats;

import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Component
public class SubDomainPopularityStatCalculator extends AbstractPopularityStatCalculator {
    
    public SubDomainPopularityStatCalculator() {
        super(BoardGame::getSubDomains);
    }
}