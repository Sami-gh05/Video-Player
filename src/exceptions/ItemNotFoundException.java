package exceptions;

public class ItemNotFoundException extends InvalidCredentialsException{
    public ItemNotFoundException(String message){
        super(message);
    }
}
