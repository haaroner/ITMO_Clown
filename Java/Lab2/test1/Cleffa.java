import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Cleffa extends Pokemon { 
	double hp = 50D, att = 25D, def = 28D, spAtt = 45D, spDeff = 55D, sp = 15D;

	public Cleffa(String name, int level) {
		super(name, level);
		this.setType(Type.FAIRY);
		this.setStats(hp, att, def, spAtt, spDeff, sp);
		DreamEater CleffaAttack1 = new DreamEater();
		FireBlast CleffaAttack2 = new FireBlast();
		this.addMove(CleffaAttack1);
		this.addMove(CleffaAttack2);
	}
}
