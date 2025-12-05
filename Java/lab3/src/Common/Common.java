package Common;

import Creature.Creature;
import NameRecord.NameRecord;

interface Hello {
   void sayHello(String nameString, String greeting);
}

public abstract class Common {
   protected String name;
   protected boolean alive;
   NameRecord curNameRecord;
   public Common(String name){
      this.name = name;
      alive = true;
      curNameRecord = new NameRecord(name, 20);
      
      Hello hello = new Hello()
      {
         public void sayHello(String nameString, String greeting){
            if(nameString != null && nameString.length()>0){

            System.out.println(nameString + greeting);
            }
            else{
               throw new IllegalArgumentException("wrong null name");
            }
         }
      };

      hello.sayHello(this.name," появился");
   }
   
   public void die() {
      this.alive = false;
      System.out.println(this.curNameRecord.name()+" умирает в мучениях");
   }

   public String getName(){
      return this.name;
   }

   public boolean isAlive(){
      return this.alive;
   }

   @Override
   public int hashCode() {
      return java.util.Objects.hash(name, alive);
   }

   @Override
   public boolean equals(Object obj) {
      return this.hashCode() == obj.hashCode();
   }

   @Override 
   public String toString() {
      return "name: "+this.name + " alive: "+ this.alive + " hash: "+ this.hashCode();
   }
}