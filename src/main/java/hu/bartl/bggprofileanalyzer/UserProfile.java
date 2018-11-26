package hu.bartl.bggprofileanalyzer;

import lombok.Data;

import java.util.Set;

@Data
public class UserProfile {
    
    private String userId;
    
    private Set<Integer> ownedGameIds;
}
