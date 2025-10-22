import ru.ifmo.se.pokemon.*;
import java.util.Random;
//BUG REPORT IN PHANPY FILE
public class Main
{
	public static void main(String[] arg)
	{
		Random random = new Random();
		for(int i = 0; i < 3; i++) {

			Battle b = new Battle();
			Pokemon p1 = new Rotom("Ivan", i+1+random.nextInt(5));
			Pokemon p2 = new Phanpy("Volodya", i+1+random.nextInt(5));
			Pokemon p3 = new Donphan("Mike", i+1+random.nextInt(5));
			Pokemon p4 = new Cleffa("Clown", i+1+random.nextInt(5));
			Pokemon p5 = new Clefairy("__", i+1+random.nextInt(5));
			Pokemon p6 = new Clefable("abobus", i+1+random.nextInt(5));

			b.addAlly(p1);
			b.addAlly(p2);
			b.addAlly(p3);
			b.addFoe(p4);
			b.addFoe(p5);
			b.addFoe(p6);

			b.go();
			System.out.println("BATTLE ENDED");
		}
	}
}
