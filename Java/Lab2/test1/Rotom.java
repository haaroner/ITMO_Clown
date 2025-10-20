import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Stat;

public class Rotom extends Pokemon
{
	private double hp = 50.0, att = 50.0, def = 77.0, spAtt = 95.0, spDef = 77.0, speed = 91.0;
	public Rotom(String name, int level)
	{
		super(name, level); //name and level of Rotom
		setType(Type.ELECTRIC, Type.GHOST);
		setStats(hp, att, def, spAtt, spDef, speed);
	}
}
