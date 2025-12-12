package Characters;
import java.util.ArrayList;
import java.util.Random;

import Exceptions.MyException;
import Things.Thing;
import Utils.Place;
import Utils.ThingType;
import Utils.CreatureState;


public final class Actor extends Creature {
   public Actor(String name, int attackPower, String bio) {
      super(name, true, attackPower, bio);
      //cur_state = CreatureState.Common;
   }

   public void show(ArrayList<Thing> new_Objects) {
      this.GameObjects = new_Objects;
      Random random = new Random();
      if(random.nextInt(10) < 5)
         System.out.println("После всего предыдущего мы не очень удивились находке, скажу больше -- были почти к ней готовы");
      else
         System.out.println("Находка показалась нам странной, рядом с лагерем такие вещи не каждый день встретишь. В любом случае, мы решили подойти и узнать что это такое.");
   }

   public void moveTo(Place place_to) throws Exception{
      Random random = new Random();
      this.cur_Place = place_to;
      if(this.alive == false) {
         System.out.println(name + " try to go somewhere, but he is dead haha");
         return;
      }
      if(place_to == Place.OutOfWorld)
         System.out.println(this.name+" Пошли гулять дальше c " + this.curState+ " настроением");
      else if(place_to == Place.Camp)
         System.out.println(this.name+" Подошли к территории рядом с лагерем с " + this.curState + " настроением");
      else
         throw new Exception("UNEXPECTED PLACE STOP THE STORY!!!");

      //System.out.println("В это время у героя " + this.name+ " было "+this.curState + " настроение");

      if(random.nextInt(10) <= 6)
         System.out.println("Уффф");

      this.scanWorld();
   }

   protected void scanWorld() throws MyException {
      //It came to camp
      if(this.cur_Place == Place.Camp)
      {
         if(this.GameObjects != null)
         {
            for(Thing CurThing: this.GameObjects)
            {
               if(CurThing.getType() == ThingType.back)
                  if(CurThing.isEmpty() == false)
                  {
                      ArrayList<Thing> result = CurThing.getContent();
                      System.out.println("В санях лежали " + result.size() + " покрывшихся снегом непонятных объекта");
                      for(Thing newThing: result) {


                     if(newThing.getType() == ThingType.back)
                        System.out.println(this.name + " достали что-то похожее на " + newThing.getName() + " которое выглядит как " + newThing.getDescription());

                     if(newThing.getType() == ThingType.Hero) {
                           System.out.println("Это оказалось тело " + newThing.getDescription());
                        if(newThing.isAlive() == true)
                           System.out.println("Ого, оно еще живое, привет "+ newThing.toString());
                        else {
                           System.out.println("Надо срочно его спасать!!! "+ newThing.toString());
                           if(newThing.interract()){
                              System.out.println(this.name + " о чудо! Я смог спасти " + newThing.getName());
                              changeMood(CreatureState.Happy);
                           }
                           else {
                              System.out.println(this.name + " Закрыв тело " + newThing.getName() + " брезентом, мы стояли над ним в глубокой задумчивости");
                              changeMood(CreatureState.Sad);
                           }
                        }
                     }
                  }
                     
                  }
            }
         }
         //TODO:
         /*Открытие+закрытие, реакция диалогом, услышать звук и пойти (просто принтом когда уходишь обратно) разделить приход и уход для встречи с гопотой*/
      }
      if(this.cur_Place==Place.OutOfWorld) {
         if(this.curState == CreatureState.Happy) {
            System.out.println("Веселые пошли на поиски новых приключений");
         }
         else if(this.curState == CreatureState.Interested){
            System.out.println("Очень земные и хорошо знакомые нам звуки были настолько неожиданны в этом мире пагубы и смерти, что, опрокидывая все наши представления о космической гармонии, ошеломили нас сильнее, чем это сделали бы самые невероятные звучания и шумы.");
         }
      }
   }

   //TODO:
   /*Скопировать из энеми
   Приходим
   Проверяем все неживые объекты смотрим что внутри спасаем если живое грустим если мертвое moveTo обратное не делаем, чтоб можно было пересечься с злодеями 
   Когда идем обратно - услышать звук и написать что-нибудь пафосное*/
}
