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
		if(random.nextInt(10) < 7)
		{
			Effect e = new Effect();
			e.burn(def);
			def.setCondition(e);
		}
	}
	
	protected String describe()
	{
		return "burned";
	}

}
