import ru.ifmo.se.pokemon.*;

public final class Rotom extends Pokemon
{
	private double hp = 50.0, att = 50.0, def = 77.0, spAtt = 95.0, spDef = 77.0, speed = 91.0;
	public Rotom(String name, int level)
	{
		super(name, level); //name and level of Rotom
		setType(Type.ELECTRIC, Type.GHOST);
		setStats(hp, att, def, spAtt, spDef, speed);
		Confide RotomAttack1 = new Confide();
		ThunderWave RotomAttack2 = new ThunderWave();
		ConfuseRay RotomAttack3 = new ConfuseRay();
		Will_O_Wisp RotomAttack4 = new Will_O_Wisp();
		this.setMove(RotomAttack1, RotomAttack2, RotomAttack3, RotomAttack4);
	}
}
