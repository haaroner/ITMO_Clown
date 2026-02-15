package models;

import Utility.Element;
import com.opencsv.bean.CsvBindByName;
import interfaces.Validatable;

public class Location extends Element implements Validatable {
    private static Integer maxId = 0;
    @CsvBindByName private Integer id = 0;
    @CsvBindByName private long x;
    @CsvBindByName private Float y; //Поле не может быть null
    @CsvBindByName private Double z; //Поле не может быть null

    public Location(Integer id, long x, Float y, Double z) {
        this.id = id;
        maxId = Math.max(this.id, maxId);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location() {}

    @Override
    public boolean validate(){
        return id != null && id > 0 && y != null && z != null;
    }

    public static Integer getMaxId() {
        return maxId;
    }


    @Override
    public String toString() {
        if(this.validate())
            return "id: " + String.valueOf(id) + " x: " + String.valueOf(x) + " y: " + String.valueOf(y) + " z: " + String.valueOf(z);
        else
            return "unvalidatable";
    }

    public void updateMaxId() {
        maxId = Math.max(this.id, maxId);
    }

    @Override
    public Integer getId() {
        return id;
    }
}
