package com.pwbsoft.jserialmailer.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends Exception {
    private Error error;

    public ApplicationException(Error error) {
        super();
    }

    public ApplicationException(Error error, String message) {
        super(message);
    }

    public ApplicationException(Error error, Throwable cause) {
        super(cause);
    }

    public ApplicationException(Error error, String message, Throwable cause) {
        super(message, cause);
    }
}
