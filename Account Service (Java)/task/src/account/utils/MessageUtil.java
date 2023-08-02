package account.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    public String getMessageByCode(String code) {
        return messageSource.getMessage(code, new Object[0], Locale.ENGLISH);
    }
}
