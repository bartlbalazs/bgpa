package hu.bartl.bggprofileanalyzer.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PresentationConfiguration {

    @Value("${app.others-default-group-max-ratio}")
    private int othersMaxRatio;

    @Value("${app.artist-group-max-ratio}")
    private int otherArtistMaxRatio;

    @Value("${app.designer-group-max-ratio}")
    private int otherDesignerMaxRatio;

    @Value("${app.mechanic-group-max-ratio}")
    private int otherMechanicMaxRatio;

    @Value("${app.family-group-max-ratio}")
    private int otherFamilyMaxRatio;

    @Value("${app.subdomain-group-max-ratio}")
    private int otherSubdomainMaxRatio;

    @Value("${app.default-minimal_group_size}")
    private int minimalGroupSize;

    @Value("${app.subdomain-minimal_group_size}")
    private int minimalSubdomainGroupSize;

}
