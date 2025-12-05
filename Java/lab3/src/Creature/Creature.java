package Creature;

import java.util.ArrayList;
import java.util.Random;

import Common.Common;
import Thing.Thing;
import Place.Place;
import CreatureState.CreatureState;
import MyException.MyException;
import Coin.Coin;


interface movable {
   public void moveTo(Place place_to) throws Exception;
}

public abstract class Creature extends Common implements movable {
   protected int attackPower;
   protected CreatureState curState;
   protected Place cur_Place;
   protected Creature OpponentCreature;
   protected ArrayList<Thing> GameObjects;
   public Creature(String name, boolean alive, int attackPower) {
      super(name);
      this.alive = alive;
      this.cur_Place = Place.OutOfWorld;
      this.curState = CreatureState.Common;
      this.attackPower = attackPower;
   }

   protected void fight(Creature opponent) throws InterruptedException
   {
      Coin myCoin = new Coin();
      Random random = new Random();
      System.out.println("Лицом к лицу встретились "+this.name + " и " + opponent.getName());
      System.out.println("fight");
      while(true){
         System.out.println(this.getName() + " наносит удар");
         if(myCoin.flipCoin(this.attackPower))
         {
            opponent.die();
            break;
         }
         System.out.println("Промах!");
         Thread.sleep(3000);
         System.out.println(opponent.getName() + " наносит удар");
         if(myCoin.flipCoin(opponent.getAttackPower()))
         {
            this.die();
            break;
         }
         System.out.println("Промах!");
         Thread.sleep(3000);
      }
   }

   protected boolean fight(Thing opponent) throws InterruptedException
   {
      Coin myCoin = new Coin();
      Random random = new Random();
      System.out.println("Лицом к лицу встретились "+this.name + " и " + opponent.getName());
      System.out.println("fight");
      while(true){
         System.out.println(this.getName() + " наносит удар");
         if(myCoin.flipCoin(this.attackPower))
         {
            opponent.die();
            return true;
         }
         System.out.println("Промах!");
         Thread.sleep(3000);
         System.out.println(opponent.getName() + " пытается сбежать");
         if(myCoin.flipCoin(opponent.getAgility()))
         {
            System.out.println(opponent.getName() + " удалось скрыться, герой выжил!!");
            return false;
         }
         System.out.println(" не получилось!");
         Thread.sleep(3000);
      }
   }

   public int getAttackPower() {
      return this.attackPower;
   }

   public void moveTo(Place place_to) throws Exception {
      this.cur_Place = place_to;
      this.scanWorld();
   }

   public void introduce(Creature new_Creature) {
      this.OpponentCreature = new_Creature;
   }

   public Place getPlace() {
      return this.cur_Place;
   }

   public void changeMood(CreatureState mood) throws MyException {
      this.curState = mood;
      if(mood == CreatureState.Horror)
         System.out.println(this.name + " Испугался досмерти");
      else if(mood == CreatureState.Interested)
      {
         System.out.println(" ошеломили его, и он еще долго не мог пошевелиться");
         try {
            Thread.sleep(3000);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      else
         System.out.println(this.name + " теперь " + mood);
   }

   public void show(ArrayList<Thing> new_Objects) {
      this.GameObjects = new_Objects;
   }
   protected void scanWorld() throws MyException {
   }
}
