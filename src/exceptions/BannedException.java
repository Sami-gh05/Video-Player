package exceptions;

public class BannedException extends InvalidCredentialsException{
    public BannedException(String message){
        super(message);
    }
}
