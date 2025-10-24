import ru.ifmo.se.pokemon.*;

public final class Blizzard extends SpecialMove 
{
	public Blizzard() {
		super(Type.ICE, 110, 70);
	}

	protected void applyOppDamage(Pokemon def, double damage) {
		def.setMod(Stat.HP, (int)damage);
		Effect e = new Effect().chance(0.1);
		e.freeze(def);
		def.setMod(Stat.HP, (int)Math.round(damage));
	}

	protected String describe() {
		return "BLIZZARD";
	}
}
