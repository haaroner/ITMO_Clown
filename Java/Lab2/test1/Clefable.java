import ru.ifmo.se.pokemon.*;

public final class Clefable extends Clefairy
{
	public Clefable(String name, int level) {
		super(name, level);
		this.setStats(95.0, 70.0, 73.0, 95.0, 90.0, 60.0);
		Blizzard ClefableAttack1 = new Blizzard();
		this.addMove(ClefableAttack1);
	}
}
