package hu.bartl.bggprofileanalyzer.data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PlayerCountRecommendation implements Serializable {

    private int numplayers;
    private int best;
    private int recommended;
    private int notRecommended;

    public boolean isRecommended() {
        return best + recommended >= notRecommended;
    }

    public boolean isBest() {
        return best >= recommended + notRecommended;
    }
}
