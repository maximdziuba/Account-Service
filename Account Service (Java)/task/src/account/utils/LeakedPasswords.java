package account.utils;

import java.util.List;

public class LeakedPasswords {

    private static final List<String> LEAKED_PASSWORDS = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    public static boolean contains(String password) {
        return LEAKED_PASSWORDS.contains(password);
    }
}
