import ru.ifmo.se.pokemon.*;

public final class Slam extends PhysicalMove {
	public Slam () {
		super(Type.NORMAL, 80, 75);
	}
	//no redefenition of applyDamage cause it deals damage 
	//without conditions
	
	protected String describe() {
		return "Slam";
	}
}
