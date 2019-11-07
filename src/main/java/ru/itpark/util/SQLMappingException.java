package ru.itpark.util;

public class SQLMappingException extends RuntimeException {
    public SQLMappingException() {
        super();
    }

    public SQLMappingException(String message) {
        super(message);
    }

    public SQLMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLMappingException(Throwable cause) {
        super(cause);
    }

    protected SQLMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
