package account.exception;

public class PeriodNotFoundException extends RuntimeException {
    public PeriodNotFoundException(String message) {
        super(message);
    }
}
