package CreatureState;

public enum CreatureState {
   Happy("Happy"),
   Sad("Sad"),
   Common("Common"),
   Interested("Interested"),
   Horror("Frightened to death");
   String data;
   private CreatureState(String data) {
      this.data = data;
   }
   public String getData() {
      return data;
   }
}
