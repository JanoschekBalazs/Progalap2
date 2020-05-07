package GUI;

import java.util.Objects;

public class Field {

    public static final int DETONATED = 9;
    public static final int HIDDEN = -1;

    private Coordinates coordinates;
    private int state;

    public Field(Coordinates coordinates, int state){
        this.coordinates = coordinates;
        this.state = state;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getState(){
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;
        Field field = (Field) o;
        return coordinates.equals(field.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
