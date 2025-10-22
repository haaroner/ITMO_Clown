import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Cleffa extends Pokemon
{
	double hp = 50.0, att = 25.0, deff = 28.0, spAtt = 45.0, spDeff = 55.0, speed = 15.0;
	public Cleffa(String name, int level)
	{
		super(name, level);
		setType(Type.FAIRY);
		setStats(50D, 25D, 28D, 45D, 55D, 15D);
		DreamEater CleffaAttack1 = new DreamEater();
		FireBlast CleffaAttack2 = new FireBlast();
		this.setMove(CleffaAttack1, CleffaAttack2);
	}
}
