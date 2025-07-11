package com.framework.common.exceptions;

public class ChallengeProcessingException extends RuntimeException {
    public ChallengeProcessingException(String message) {
        super(message);
    }

    public ChallengeProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
