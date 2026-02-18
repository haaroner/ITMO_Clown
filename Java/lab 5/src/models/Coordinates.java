package Models;

import Utility.Element;
import Interfaces.Validatable;
import com.opencsv.bean.CsvBindByName;

public final class Coordinates extends Element implements Validatable{
    private static Integer maxId = 0;
    @CsvBindByName private Integer id = 0;
    @CsvBindByName private int x;
    @CsvBindByName private double y;

    public Coordinates(Integer id, int x, double y) {
        this.id = id;
        maxId = Math.max(this.id, maxId);
        this.x = x;
        this.y = y;
    }

    public Coordinates() {}

    @Override
    public boolean validate() {
        return this.id != null && this.id >= 1;
    }

    public static Integer getMaxId() {
        return maxId;
    }

    public void updateMaxId() {
        maxId = Math.max(this.id, maxId);
    }

    @Override
    public String toString() {
        if(this.validate())
            return "x: " + String.valueOf(x) + " y: " + String.valueOf(y);
        else
            return "unvalidatable";
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
