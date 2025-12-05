package Alliens;

import java.util.ArrayList;
import java.util.Random;
import Creature.Creature;
import Thing.Thing;
import Place.Place;
import CreatureState.CreatureState;
import MyException.MyException;
import ThingType.ThingType;
import Sound.Sound;
public final class Alliens extends Creature{
   private Thing temp;
   private Sound horribleSound;
   public Alliens(String name, int attackPower) {
      super(name, true, attackPower);
      this.horribleSound = new Sound("Страшный рык", CreatureState.Horror);
   }

   public void moveTo(Place place_to) throws Exception{
      Random random = new Random();
      this.cur_Place = place_to;
      if(this.alive == false) {
         System.out.println(name + " try to go somewhere, but he is dead haha");
         return;
      }
      if(place_to == Place.OutOfWorld)
         System.out.println(name+" быстро уходят из лагеря");
      else if(place_to == Place.Camp)
         System.out.println(name+" подло крадутся к лагерю, пока их никто не видит");
      else
         throw new MyException("UNEXPECTED PLACE - STOP THE STORY!!!", "test");

      if(random.nextInt(10) <= 6)
         System.out.println("BRRRRR!");

      this.scanWorld();
   }

   @Override
   public void changeMood(CreatureState my_state) throws MyException {
      throw new MyException("This creature doesnt have any emotions =<", this.toString());
   }

   protected void scanWorld() throws MyException{
      //It came to camp
      if(this.cur_Place == Place.Camp)
      {
         //kills main characters
         //System.out.println()
         try{
            horribleSound.occure(this.OpponentCreature);
            System.out.println(this.name + " Рыщет в поиске жертв, BRRR!");
            if(this.OpponentCreature.getPlace() == Place.Camp)
            {
               System.out.println(this.name + " внезапно наткнулся на " + this.OpponentCreature.getName() + " и погнался за ним");
               try {
                  this.fight(OpponentCreature);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
              // this.OpponentCreature.die();
            }
         }
         catch (RuntimeException r){
            System.out.println("cant find any creature");
         }
         System.out.println("");
         for(int i = 0; i < 10; i++)
         {
            System.out.print(".");
            try {
               Thread.sleep(500);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
         System.out.println(this.name + " продолжают рыскать по лагерю в поисках жертв");
          ArrayList<Thing> buffer = new  ArrayList<>();
         for(Thing CurThing: this.GameObjects)
         {
            if(CurThing.getType() == ThingType.Hero)
            {
               try {
                  if(this.fight(CurThing))
                     buffer.add(CurThing);
               } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               //CurThing.die();               
               this.temp = CurThing;
            }
         }
         for(Thing CurThing: this.GameObjects)
         {
            if(CurThing.getType() == ThingType.back)
               CurThing.putContent(buffer);
            //CurThing.putContent(CurThing);--------------
         }
         try {
            moveTo(Place.OutOfWorld);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         //TODO:
         /*сделать цикл по неживым объектам, находим такой, перекладываем последний трупик и уходим */
      }
   }
}
