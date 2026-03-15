package Utility;

import Interfaces.Validatable;

/**
 * Abstract Element class
 * Superclass of {@link Models.Route},{@link Models.Location}, {@link Models.Coordinates}
 * @author Clown
 */
public abstract class Element implements Validatable{
    private Integer id;
    abstract public Integer getId();
    abstract public void updateMaxId();
}
