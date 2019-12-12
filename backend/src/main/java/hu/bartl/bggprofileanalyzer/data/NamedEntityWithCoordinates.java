package hu.bartl.bggprofileanalyzer.data;

import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
public class NamedEntityWithCoordinates extends NamedEntityWithPicture {

    private double x;
    private double y;
    private double r;

    @Builder(builderMethodName ="withCoordinates", toBuilder = true)
    public NamedEntityWithCoordinates(int id, String name, String thumbnail, double x, double y, double r) {
        super(id, name, thumbnail);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public NamedEntityWithCoordinates(int id, String name, String thumbnail) {
        super(id, name, thumbnail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        NamedEntityWithCoordinates that = (NamedEntityWithCoordinates) o;
        return
            that.getId() == getId() &&
            Double.compare(that.getX(), getX()) == 0 &&
            Double.compare(that.getY(), getY()) == 0 &&
            Double.compare(that.getR(), getR()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), super.getId(), getX(), getY(), getR());
    }
}
