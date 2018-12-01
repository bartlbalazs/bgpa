package hu.bartl.bggprofileanalyzer.data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
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
    private Set<NamedEntity> artist;
    private Set<NamedEntity> designers;
    private Set<NamedEntity> expansions;
    private Set<NamedEntity> families;
    private Set<NamedEntity> categories;
    private Set<NamedEntity> mechanics;
    private Set<NamedEntity> subDomains;
}
