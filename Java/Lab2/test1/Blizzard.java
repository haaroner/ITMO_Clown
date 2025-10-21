import ru.ifmo.se.pokemon.*;
import java.util.Random;

public class Blizzard extends SpecialMove 
{
	Random random = new Random();
	public Blizzard()
	{
		super(Type.ICE, 110, 70);
	}

	protected void applyOppDamage(Pokemon def, double damage)
	{
		def.setMod(Stat.HP, (int)damage);
		Effect e = new Effect().chance(0.1);
		e.freeze(def);
	}

	protected String describe()
	{
		return "BLIZZARD";
	}
}
