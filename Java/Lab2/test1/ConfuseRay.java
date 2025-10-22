import ru.ifmo.se.pokemon.*;

public final class ConfuseRay extends StatusMove {
   public ConfuseRay() {
      super(Type.GHOST, 1, 100);
   }

   protected void applyOppEffects(Pokemon p) {
      Effect effect = new Effect();
      effect.confuse(p);
   }
}