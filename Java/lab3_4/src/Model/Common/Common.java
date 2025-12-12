package Models.Common;

import Utils.NameRecord;
import Interfaces.Hello;


public abstract class Common {
   protected String name;
   protected boolean alive;
   protected NameRecord curNameRecord;
   protected String bio;
   
   public Common(String name, String bio){
      this.name = name;
      alive = true;
      curNameRecord = new NameRecord(name, 20);
      this.bio = bio;
      
      Hello hello = new Hello() {
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
      int hash = 0;
      for(char c: name.toCharArray()) {
         hash+=(int) c;
      }
      if(alive == false)
         hash*=-1;
      return hash;
   }

   public String getBio() {
      return bio;
   }

   public int getAge() {
      return curNameRecord.age();
   }

   @Override
   public boolean equals(Object obj) {
      if(obj == this) 
         return true;
      else if((this.getClass() != obj.getClass()) || (obj == null))
         return false;
      else {
         Common myObj = (Common) obj;
         return (this.toString() == myObj.toString())&&(this.curNameRecord.age() == myObj.getAge())&&(this.hashCode()==myObj.hashCode());
      }
   }

   @Override 
   public String toString() {
      return "name: "+this.name + " alive: "+ this.alive + " hash: "+ this.hashCode() + " bio : " + this.bio;
   }
}