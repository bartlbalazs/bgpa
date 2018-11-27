package hu.bartl.bggprofileanalyzer.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class Mechanic implements Serializable {
    
    private int id;
    private String name;
}
