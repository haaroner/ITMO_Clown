import ru.ifmo.se.pokemon.*;

public final class MeteorMash extends PhysicalMove
{
   public MeteorMash()
   {
      super(Type.STEEL, 90, 90);
   }
   
   protected void applyOppDamage(Pokemon def, double damage)
   {
      def.setMod(Stat.HP, (int)Math.round(damage));
   }

   protected void applySelfDamage(Pokemon att, double damage)
   {
	Effect e = new Effect().chance(0.2);
	e.stat(Stat.ATTACK, 1);
	att.addEffect(e);
   }

   protected String describe() {
      return "METEOR MASH";
   } 
}
