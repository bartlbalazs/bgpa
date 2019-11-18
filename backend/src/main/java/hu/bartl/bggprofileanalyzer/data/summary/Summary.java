package hu.bartl.bggprofileanalyzer.data.summary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Summary {

    private long allItemsCount;

    private long expansionsCount;

    private long ksItemsCount;

    long minutesToPlay;
}
