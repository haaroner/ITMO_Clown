package Coin;

import java.util.Random;

public final class Coin {
   public Coin (){

   }
   public boolean flipCoin(int probability) {
      Random random = new Random();
      boolean result;
      System.out.println("подбрасываем кубик!");
      for(int i = 0; i < 5; i++) {
         try {
            Thread.sleep(50);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         System.out.print(".");
      }
      result = random.nextInt(10) < probability;
      if(result == false)
         System.out.print("  неудача!!");
      else
         System.out.print("  повезло!!");
      return result;
   }
}
