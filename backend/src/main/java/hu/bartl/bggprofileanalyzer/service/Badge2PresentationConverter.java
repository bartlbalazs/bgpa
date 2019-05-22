package hu.bartl.bggprofileanalyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bartl.bggprofileanalyzer.data.badge.Badge;
import hu.bartl.bggprofileanalyzer.data.badge.BadgePresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class Badge2PresentationConverter {

    private final ObjectMapper mapper;
    private Map<String, BadgePresentation> badgePresentationMap;

    @Value("${app.badge-descriptor-path}")
    private String badgeDescriptorPath;


    public Badge2PresentationConverter(@Qualifier("yamlMapper") ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void initialize() throws IOException {
        List<BadgePresentation> badges = mapper.readValue(new ClassPathResource(badgeDescriptorPath).getFile(),
                new TypeReference<List<BadgePresentation>>() {
                });
        badgePresentationMap = badges.stream().collect(Collectors.toMap(BadgePresentation::getId, Function.identity()));
    }

    public BadgePresentation convert(Badge badge) {
        return badgePresentationMap.get(badge.getId());
    }
}
