package com.brageast.mirror.exception;

/**
 * 禁止使用异常
 *
 */
public class ProhibitedUseException extends Exception {
    public ProhibitedUseException() {

    }

    public ProhibitedUseException(String msg) {
        super(msg);
    }
}
