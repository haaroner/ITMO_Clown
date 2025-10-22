import ru.ifmo.se.pokemon.*;

public class Clefairy extends Cleffa {

   public Clefairy(String name, int level) {
      super(name, level);
      this.setStats(70D, 45D, 48D, 60D, 65D, 35D);
      MeteorMash ClefairyAttack1 = new MeteorMash();
      this.addMove(ClefairyAttack1);
   }
}