package hu.bartl.bggprofileanalyzer.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PresentationConfiguration {

    @Value("${app.others-default-group-max-ratio}")
    private int others_max_ratio;

    @Value("${app.artist-group-max-ratio}")
    private int other_artist_max_ratio;

    @Value("${app.designer-group-max-ratio}")
    private int other_designer_max_ratio;

    @Value("${app.mechanic-group-max-ratio}")
    private int other_mechanic_max_ratio;

    @Value("${app.default-minimal_group_size}")
    private int minimalGroupSize;
}
