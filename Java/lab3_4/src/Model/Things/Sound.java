package Things;

import Characters.Creature;
import Things.Thing;
import Utils.CreatureState;
import Utils.ThingType;
import Exceptions.MyException;

public final class Sound extends Thing{
   private CreatureState soundEffect;
   public Sound(String name, CreatureState soundEffect) {
      super(name, ThingType.Sound, "", 0);
      this.soundEffect = soundEffect;
   }

   public void occure(Creature myCreature) throws MyException {
         System.out.println(this.name + " раздалcя где-то в далеке");
         myCreature.changeMood(soundEffect);
   }
}
