package hu.bartl.bggprofileanalyzer.data;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

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
}
