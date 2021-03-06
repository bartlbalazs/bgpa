package hu.bartl.bggprofileanalyzer.data;

import java.io.Serializable;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BoardGame implements Serializable {

    private int id;
    private int yearpublished;
    private int minplayers;
    private int maxplayers;
    private int playingtime;
    private int minplaytime;
    private int maxplaytime;
    private int age;
    private int owned;
    private int rank;
    private int familyRank;
    private double userRating;
    private double weight;
    private String name;
    private String thumbnail;
    private String image;
    private Set<NamedEntity> artists;
    private Set<NamedEntity> designers;
    private Set<NamedEntity> expansions;
    private Set<NamedEntity> families;
    private Set<NamedEntity> categories;
    private Set<NamedEntity> mechanics;
    private Set<NamedEntity> subDomains;
    private Set<PlayerCountRecommendation> playerCountRecommendations;
}
