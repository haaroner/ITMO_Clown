package Builders;

import Models.Coordinates;

public final class CoordinatesBuilder {
    public CoordinatesBuilder CoordinatesBuilder(){
        return this;
    }

    public CoordinatesBuilder setId() {
        this.id = Coordinates.getMaxId()+1;
        return this;
    }

    public CoordinatesBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public CoordinatesBuilder setY(double y) {
        this.y = y;
        return this;
    }

    public Coordinates build() {
        Coordinates newCoordinates = new Coordinates(this.id, this.x, this.y);
        if(newCoordinates.validate())
            return newCoordinates;
        else
            return null;
    }

    private Integer id;
    private int x;
    private double y;
}
