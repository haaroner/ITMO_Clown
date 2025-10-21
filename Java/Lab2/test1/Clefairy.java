import ru.ifmo.se.pokemon.*;

public class Clefairy extends Cleffa {

   public Clefairy(String name, int level) {
      super(name, level);

      MeteorMash ClefairyAttack1 = new MeteorMash();
      this.addMove(ClefairyAttack1);
   }
}