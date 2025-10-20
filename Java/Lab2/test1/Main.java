import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main
{
	public static void main(String[] arg)
	{
		Battle b = new Battle();
		Pokemon p1 = new Cleffa("Clown2", 2);
		Pokemon p2 = new Rotom("Clown1", 4);

		b.addAlly(p1);
		b.addFoe(p2);

		b.go();
	}
}
