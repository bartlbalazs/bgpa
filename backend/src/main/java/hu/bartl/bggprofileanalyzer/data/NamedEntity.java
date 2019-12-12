package hu.bartl.bggprofileanalyzer.data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class NamedEntity implements Serializable {

    protected final int id;
    protected final String name;
}
