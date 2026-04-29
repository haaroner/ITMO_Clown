package utility;

import interfaces.Validatable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Abstract Element class
 * Superclass of {@link models.Route},{@link models.Location}, {@link models.Coordinates}
 * @author Clown
 */
public abstract class Element implements Validatable{
    private Integer id;
    public abstract Integer getId();
    public abstract void updateMaxId();
    public abstract String getSaveQuery();
    public abstract void setId(Integer newId);
    public abstract PreparedStatement formSaveQuery(PreparedStatement statement) throws SQLException;
}
