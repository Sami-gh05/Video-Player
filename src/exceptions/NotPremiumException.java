package exceptions;

public class NotPremiumException extends InvalidCredentialsException{
    public NotPremiumException(String message){
        super(message);
    }
}
