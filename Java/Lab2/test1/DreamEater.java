import ru.ifmo.se.pokemon.*;

public final class DreamEater extends SpecialMove
{
	double power = 100.0, accuracy = 100.0;
	private Status oppStatus;
	public DreamEater()
	{
		super(Type.PSYCHIC, 100, 100);
	}

	//Attack is implied only if def pokemon is in sleep
	protected void applySelfDamage(Pokemon att, double damage)
	{
		if(oppStatus == Status.SLEEP)
		{
			att.setMod(Stat.HP, (int)Math.round(-1.0 * (att.getStat(Stat.HP) - att.getHP()) / 2.0));
		}
	}

	protected void applyOppDamage(Pokemon def, double damage)
	{
		oppStatus = def.getCondition();
		if(oppStatus == Status.SLEEP)
		{
			def.setMod(Stat.HP, (int)Math.round(damage));
		}
	}
	
	protected String describe()
	{
		return "ест сон";
	}
}
