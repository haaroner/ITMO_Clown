import java.util.ArrayList;
import java.util.Random;
import Thing.Thing;
import Alliens.Alliens;
import Actor.Actor;
import CreatureState.CreatureState;
import Thing.Thing;
import ThingType.ThingType;
import Sound.Sound;
import Place.Place;

public class Main {
   public static void main(String[] args) throws Exception {
      //Initialize all objects
      ArrayList<Thing> background = new ArrayList<>();
      Alliens gopniki = new Alliens("gopniki", 5);
      Actor vasya = new Actor("Vasya", 2);//------name-null--- ili udalit obj
      Thing dog = new Thing("bobik", ThingType.Hero, "пропавшая собака", 7);
      Thing Telo = new Thing("Telo", ThingType.Hero, "давний знакомый Гедни", 3);
      Thing tuk = new Thing("grobik", ThingType.back, "старый брезент, под которым что-то лежит", 0);
      Sound mySound = new Sound("Земные знакомые звуки", CreatureState.Interested);
      //plot

      background.add(dog);
      background.add(tuk);
      background.add(Telo);
      gopniki.introduce(vasya);
      gopniki.show(background);
      //----------you can change-----------
      System.out.println("История начинается");
      System.out.println("-----------------------------------------");
      vasya.show(background);
      vasya.moveTo(Place.Camp);
      gopniki.moveTo(Place.Camp);
      
      
      

      //Show which objects to scan
      vasya.introduce(gopniki);
     mySound.occure(vasya);
      vasya.moveTo(Place.OutOfWorld);
      System.out.println("-----------------------------The end-----------------------------------");
      System.out.println("---------тест функций toString и equals---------");
      System.out.println(gopniki.toString());
      System.out.println(tuk.toString());
      System.out.println("gopniki ?= tuk " + gopniki.equals(tuk));
      System.out.println("gopniki ?= gopniki " + gopniki.equals(gopniki));
   }
}
//TODO
/*переопределить egual()...
Переопределить exception
Добавить вариативность из ТГ
Добавить вещи секцию с описанием
Выводить описание в Take a look
открытость/закрытость вещи */