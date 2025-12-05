package Sound;

import Thing.Thing;
import Creature.Creature;
import MyException.MyException;
import CreatureState.CreatureState;
import ThingType.ThingType;

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
