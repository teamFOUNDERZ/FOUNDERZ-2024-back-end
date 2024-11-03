package com.founderz.common.exception;

public class AccessDeniedException extends FounderzException {
    private static final int STATUS_CODE = 403;

    public AccessDeniedException() {
        super(STATUS_CODE);
    }

    protected AccessDeniedException(final String message) {
        super(message, STATUS_CODE);
    }

    protected AccessDeniedException(final Throwable throwable) {
        super(throwable, STATUS_CODE);
    }

    protected AccessDeniedException(final String message, final Throwable throwable) {
        super(message, throwable, STATUS_CODE);
    }
}