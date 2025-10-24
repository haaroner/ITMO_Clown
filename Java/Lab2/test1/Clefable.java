import ru.ifmo.se.pokemon.*;

public final class Clefable extends Clefairy
{
	double hp = 95D, att = 70D, def = 73D, spAtt = 95D, spDeff = 90D, sp = 60D;
	public Clefable(String name, int level) {
		super(name, level);
		this.setStats(hp, att, def, spAtt, spDeff, sp);
		Blizzard ClefableAttack1 = new Blizzard();
		this.addMove(ClefableAttack1);
	}
}
