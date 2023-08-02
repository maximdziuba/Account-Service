package account.utils;

public class CurrencyUtil {

    public static String generateCurrencyString(Long salary) {
        var dollars = salary / 100;
        var cents = salary % 100;
        return String.format("%d dollar(s) %d cent(s)", dollars, cents);
    }
}
