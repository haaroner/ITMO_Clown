import ru.ifmo.se.pokemon.*;

public class Clefairy extends Cleffa {
   double hp = 70D, att = 45D, def = 48D, spAtt = 60D, spDeff = 65D, sp = 35D;

   public Clefairy(String name, int level) {
      super(name, level);
      this.setStats(hp, att, def, spAtt, spDeff, sp);
      MeteorMash ClefairyAttack1 = new MeteorMash();
      this.addMove(ClefairyAttack1);
   }
}