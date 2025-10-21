import ru.ifmo.se.pokemon.*;
import java.util.Random;

public class FireBlast extends SpecialMove
{
	private Random random = new Random();
	public FireBlast()
	{
		super(Type.FIRE, 110, 85);
	}

	protected void applyOppDamage(Pokemon def, double damage)
	{
		//with chance 10% make def pokemon burn
		Effect e = new Effect().chance(0.1);
		e.burn(def);
		def.setMod(Stat.HP, (int)damage);
	}
	
	protected String describe()
	{
		return "burned";
	}

}
