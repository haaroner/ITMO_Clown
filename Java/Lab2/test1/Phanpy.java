import ru.ifmo.se.pokemon.*;

public class Phanpy extends Pokemon {
	public Phanpy(String name, int level){
		super(name, level);
		this.setStats(90.0, 60.0, 60.0, 40.0, 40.0, 40.0);
		this.setType(Type.GROUND);

		Confide PhanpyAttack1 = new Confide();
		Slam PhanpyAttack2 = new Slam();
		Growl PhanpyAttack3 = new Growl();
		this.setMove(PhanpyAttack1, PhanpyAttack2, PhanpyAttack3);
	}
}
