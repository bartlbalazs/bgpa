package hu.bartl.bggprofileanalyzer.data;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class NamedEntityWithPicture extends NamedEntity {

    private String thumbnail;

    @Builder(builderMethodName = "withPicture")
    public NamedEntityWithPicture(int id, String name, String thumbnail) {
        super(id, name);
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NamedEntityWithPicture that = (NamedEntityWithPicture) o;
        return Objects.equals(getThumbnail(), that.getThumbnail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getThumbnail());
    }

    @Override
    public String toString() {
        return "NamedEntityWithPicture{" +
                "id='" + this.getId() + "\'," +
                "name='" + this.getName() + "\'," +
                "thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
