import ru.ifmo.se.pokemon.*;

public final class ThunderWave extends StatusMove {
   public ThunderWave() {
      super(Type.ELECTRIC, 1, 90);
   }

   protected void applyOppEffects(Pokemon p) {
      Effect effect = new Effect();
      effect.paralyze(p);
   }

   protected String describe(){
      return "Thunder Wave";
   }
}
