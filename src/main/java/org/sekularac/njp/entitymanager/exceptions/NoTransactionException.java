package org.sekularac.njp.entitymanager.exceptions;

public class NoTransactionException extends RuntimeException {

    public NoTransactionException(String exceptionText) {
        super(exceptionText);
    }
}
