import ru.ifmo.se.pokemon.*;
import java.util.Random;

public class MeteorMash extends PhysicalMove
{
   Random random = new Random();

   public MeteorMash()
   {
      super(Type.STEEL, 90, 90);
   }
   
   protected void applyOppDamage(Pokemon def, double damage)
   {
      def.setMod(Stat.HP, (int)damage);
   }

   protected void applySelfDamage(Pokemon att, double damage)
   {
      att.setMod(Stat.ATTACK, 1);
   }

   protected String describe() {
      return "METEOR MASH";
   } 
}