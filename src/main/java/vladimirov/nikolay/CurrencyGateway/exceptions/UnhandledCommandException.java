package vladimirov.nikolay.CurrencyGateway.exceptions;

public class UnhandledCommandException extends RuntimeException{
    public UnhandledCommandException(String message) {
        super(message);
    }
}
