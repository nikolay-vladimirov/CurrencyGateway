package vladimirov.nikolay.CurrencyGateway.exceptions;

public class MissingRatesException extends RuntimeException{
    public MissingRatesException(String message) {
        super(message);
    }
}
