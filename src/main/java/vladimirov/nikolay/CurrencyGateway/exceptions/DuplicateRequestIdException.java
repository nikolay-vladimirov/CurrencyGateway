package vladimirov.nikolay.CurrencyGateway.exceptions;

public class DuplicateRequestIdException extends RuntimeException{
    public DuplicateRequestIdException(String message){
        super(message);
    }
}
