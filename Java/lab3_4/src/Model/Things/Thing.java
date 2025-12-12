package Things;

import java.util.ArrayList;
import java.util.Random;
import Exceptions.MyException;
import Utils.ThingType;
import Utils.Coin;
import Models.Common.Common;

public class Thing extends Common{
   ArrayList<Thing> content = new ArrayList<>();
   //private Thing content;
   private ThingType cur_Type;
   private String thingDescription;
   private boolean empty;
   private int agility;
   public Thing(String title, ThingType cur_Type, String description, int agility) {
      super(title, description);
      this.cur_Type = cur_Type;
      this.empty = true;
      this.thingDescription = description;
      this.agility = agility;
   }

   public ThingType getType() {
      return this.cur_Type;
   }

   public boolean isEmpty() {
      return empty;
   }

   public void putContent(Thing content) throws MyException{
      if(this.equals(content)){
         throw new MyException("Рекурсия ", "Объект "+this.getName()+" попытался вложить в себя самого себя");
      }
      this.content.add(content);
      System.out.println(content.name + " was put into "+this.name);
      empty = false;
   }

   public void putContent( ArrayList<Thing> content) throws MyException{
      for(Thing curThing: content)
      {
         if(this.equals(content)){
         throw new MyException("Рекурсия ", "Объект "+this.getName()+" попытался вложить в себя самого себя");
      }
         this.content.add(curThing);
         System.out.println(curThing.name + " was put into "+this.name);
      }
      empty = false;
   }

   public  ArrayList<Thing> getContent() {
      System.out.println("открыли "+ this.name);
      return this.content;
   }

   public String getDescription() {
      return this.thingDescription;
   }

   public int getAgility() {
      return this.agility;
   }

   public boolean interract() {
      Coin myCoin = new Coin();
      Random random = new Random();
      if(myCoin.flipCoin(3)) {
         this.alive = true;
         if(this.cur_Type == ThingType.Hero)
            System.out.println(this.name +" Воскрес из мертвых");
         else
            System.out.println(this.name +" был починен");
      }
      else {
         System.out.println("Не получилось спасти =(");
      }
      return this.alive;
   }
}
