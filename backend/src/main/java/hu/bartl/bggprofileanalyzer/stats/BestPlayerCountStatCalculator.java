package hu.bartl.bggprofileanalyzer.stats;

import com.google.common.base.Function;
import hu.bartl.bggprofileanalyzer.data.PlayerCountRecommendation;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BestPlayerCountStatCalculator extends AbstractPlayerCountStatCalculator {

    @Autowired
    public BestPlayerCountStatCalculator(StreamHelper streams) {
        super(streams);
    }

    @Override
    protected Function<PlayerCountRecommendation, Boolean> isIncluded() {
        return PlayerCountRecommendation::isBest;
    }
}
