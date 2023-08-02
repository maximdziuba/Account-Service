package account.utils;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class DateUtil {

    public static String generatePeriodString(String rawPeriod) {
        int monthNumber = Integer.parseInt(rawPeriod.split("-")[0]);
        String year = rawPeriod.split("-")[1];
        String month =  Month.of(monthNumber).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH).toString();
        return month + "-" + year;
    }

    public static int getMonthNumber(String rawPeriod) {
        return Integer.parseInt(rawPeriod.split("-")[0]);
    }
}
