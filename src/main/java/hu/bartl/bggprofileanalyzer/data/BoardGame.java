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
    private String name;
    private String thumbnail;
    private Set<Category> category;
    private Set<Category> mechanic;
}
