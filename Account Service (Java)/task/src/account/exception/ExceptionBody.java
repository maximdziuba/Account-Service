package account.exception;

import java.time.LocalDateTime;

public record ExceptionBody(LocalDateTime timestamp,
                            Integer status,
                            String error,
                            String message,
                            String path) {
}
