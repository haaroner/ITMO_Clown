package Exceptions;

public class MyException extends Exception {
   private String details;
   public MyException(String message, String details) {
        super(message); 
        this.details = details;
    }

    @Override
    public String getMessage() {
        // Возвращаем собственное форматированное сообщение
        return super.getMessage() + " (" + details + ")";
    }
}
