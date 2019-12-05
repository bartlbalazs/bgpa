package hu.bartl.bggprofileanalyzer.stats;

import com.google.common.base.Function;
import hu.bartl.bggprofileanalyzer.data.PlayerCountRecommendation;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecommendedPlayerCountStatCalculator extends AbstractPlayerCountStatCalculator {

    @Autowired
    public RecommendedPlayerCountStatCalculator(StreamHelper streams) {
        super(streams);
    }

    @Override
    protected Function<PlayerCountRecommendation, Boolean> isIncluded() {
        return PlayerCountRecommendation::isRecommended;
    }
}
