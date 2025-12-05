import ru.ifmo.se.pokemon.*;

public final class Donphan extends Phanpy {
	public Donphan(String name, int level) {
		super(name, level);
		this.setStats(90.0, 120.0, 120.0, 60.0, 60.0, 50.0);
		this.setType(Type.GROUND);
		RockPolish DonphanAttack1 = new RockPolish();
		this.addMove(DonphanAttack1);
	}
}
