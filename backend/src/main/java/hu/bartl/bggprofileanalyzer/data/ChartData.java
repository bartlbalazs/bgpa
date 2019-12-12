package hu.bartl.bggprofileanalyzer.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ChartData {

    private final String name;
    private final List<NamedEntityWithCoordinates> series;
}
